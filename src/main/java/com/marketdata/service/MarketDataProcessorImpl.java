package com.marketdata.service;

import com.marketdata.api.*;
import com.marketdata.api.model.*;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.PriceEvent;
import com.marketdata.model.VolumeEvent;

import java.util.ArrayList;
import java.util.List;

public class MarketDataProcessorImpl extends AbstractMarketDataProcessor<MarketDataEvent, Object> {
    private static int PROCESSED_COUNT = 0;

    public MarketDataProcessorImpl(InstrumentAPI instrumentAPI, DataSinkAPI dataSinkAPI) {
        super(instrumentAPI, dataSinkAPI);
    }

    public void processBatch(List<MarketDataEvent> events) {
        preProcess(events);
        List<InternalMarketData> validData = new ArrayList<>();

        for (MarketDataEvent event : events) {
            String symbol = event.getSymbol();

            InstrumentData instrumentData;
            try {
              instrumentData = instrumentAPI.lookupInstrumentBySymbol(symbol).get(0);
            } catch (Exception e) {
                continue;
            }
            if (instrumentData == null) {
                System.out.println("Invalid symbol: " + symbol);
                continue;
            }
            event.setInternalId(instrumentData.internalId());

            if (event instanceof PriceEvent) {
                super.setProcessorType("PRICE");
            } else if (event instanceof VolumeEvent) {
                setProcessorType("VOLUME");
            }
            processEvents(List.of(event), instrumentData, validData);

            PROCESSED_COUNT++;
        }


        sendToSink(validData);

        System.out.println("Total processed: " + PROCESSED_COUNT);
    }

    public InternalMarketData processPriceEvent(PriceEvent event, InstrumentData instrumentData) {
        List<InternalMarketData> internalData = validateAndTransform(List.of(event));
        return internalData.get(0);
    }

    public InternalMarketData processVolumeEvent(VolumeEvent event, InstrumentData instrumentData) {
        List<InternalMarketData> internalData = validateAndTransform(List.of(event));
        return internalData.get(0);
    }

    private void sendToSink(List<InternalMarketData> data) {
        for (InternalMarketData item : data) {
            try {
                dataSinkAPI.publishData(item);
            } catch (Exception e) {
                System.err.println("Failed to send to sink: " + e.getMessage());
            }
        }
    }

    @Override
    protected List<InternalMarketData> validateAndTransform(List<MarketDataEvent> events) {
        return super.validateAndTransform(events);
    }

    @Override
    protected InternalMarketData createInternalData(MarketDataEvent event, InstrumentData instrumentData) {
        if (event == null || instrumentData == null) {
            throw new IllegalArgumentException("Event or InstrumentData cannot be null");
        }
        if (event instanceof PriceEvent) {
            return new InternalPriceData(event.getSymbol(), MarketDataEventType.PRICE, ((PriceEvent) event).getPrice(), event.getSource(), event.getTimestamp(), null, null);
        } else if (event instanceof VolumeEvent) {
            return new InternalVolumeData(event.getSymbol(), MarketDataEventType.VOLUME, ((VolumeEvent) event).getVolume(), event.getSource(), event.getTimestamp(), null, null);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
        }
    }
}
