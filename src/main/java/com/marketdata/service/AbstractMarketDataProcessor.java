package com.marketdata.service;

import com.marketdata.api.*;
import com.marketdata.api.model.InstrumentData;
import com.marketdata.api.model.InternalMarketData;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.PriceEvent;
import com.marketdata.model.VolumeEvent;

import java.util.ArrayList;
import java.util.List;

public class AbstractMarketDataProcessor<T extends MarketDataEvent, V> {

    protected InstrumentAPI instrumentAPI;
    protected DataSinkAPI dataSinkAPI;
    public String processorType;

    public AbstractMarketDataProcessor(InstrumentAPI instrumentAPI, DataSinkAPI dataSinkAPI) {
        this.instrumentAPI = instrumentAPI;
        this.dataSinkAPI = dataSinkAPI;
    }

    public void processEvents(List<MarketDataEvent> events, InstrumentData instrumentData, List<InternalMarketData> validData) {
        for (MarketDataEvent event : events) {
            if (event instanceof PriceEvent) {
                if (processorType.equals("PRICE")) {
                    validData.add(processPriceEvent((PriceEvent) event, instrumentData));
                } else {
                    System.out.println("Wrong processor type for price event!");
                }
            } else if (event instanceof VolumeEvent) {
                if (processorType.equals("VOLUME")) {
                    validData.add(processVolumeEvent((VolumeEvent) event, instrumentData));
                } else {
                    System.out.println("Wrong processor type for volume event!");
                }
            } else {
                throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
            }
        }
    }

    public InternalMarketData processPriceEvent(PriceEvent event, InstrumentData instrumentData) {
        System.out.println("Base class processing price event: " + event);
        return null;
    }

    public InternalMarketData processVolumeEvent(VolumeEvent event, InstrumentData instrumentData) {
        System.out.println("Base class processing volume event: " + event);
        return null;
    }

    // Remove null and negative values from the list of events
    protected void preProcess(List<MarketDataEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            MarketDataEvent event = events.get(i);
            if (event instanceof PriceEvent e && e.getPrice() == null) {
                events.remove(i);
                i--;
                continue;
            }
            if ((event instanceof PriceEvent e && e.getPrice() != null && e.getPrice() <= 0)) {
                events.remove(i);
                i--;
                continue;
            }

            if (event instanceof VolumeEvent v) {
                if (v.getVolume() != null && v.getVolume() <= 0) {
                    v.setVolume(null);
                }
                if (v.getVolume() == null) {
                    events.remove(i);
                    i--;
                    continue;
                }
            }
        }
    }

    protected List<InternalMarketData> validateAndTransform(List<MarketDataEvent> events) {
        List<InternalMarketData> result = new ArrayList<>();

        for (MarketDataEvent event : events) {
            InstrumentData instrumentData = instrumentAPI.lookupInstrumentByInternalId(event.getInternalId()).get(0);
            String symbol = instrumentData.symbol();
            InternalMarketData internalData = createInternalData(event, instrumentData);

            if (internalData != null) {
                result.add(internalData);
            }
        }
        return result;
    }

    protected InternalMarketData createInternalData(MarketDataEvent event, InstrumentData instrumentData) {
        return null;
    }

    protected void setProcessorType(String price) {
        processorType = price;
    }
}
