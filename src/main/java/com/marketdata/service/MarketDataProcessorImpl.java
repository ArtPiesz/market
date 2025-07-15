package com.marketdata.service;

import com.marketdata.api.*;
import com.marketdata.api.model.*;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.api.model.MarketDataEventType;

import java.util.*;

public class MarketDataProcessorImpl extends AbstractMarketDataProcessor<MarketDataEvent, Object> {

    private static int PROCESSED_COUNT = 0;

    private final Map<MarketDataEventType, MarketDataHandler> handlerMap = new EnumMap<>(MarketDataEventType.class);

    public MarketDataProcessorImpl(InstrumentAPI instrumentAPI, DataSinkAPI dataSinkAPI) {
        super(instrumentAPI, dataSinkAPI);

        // ✅ Rejestracja handlerów do mapy — czysta delegacja
        handlerMap.put(MarketDataEventType.PRICE, new PriceEventHandler(instrumentAPI));
        handlerMap.put(MarketDataEventType.VOLUME, new VolumeEventHandler(instrumentAPI));
        handlerMap.put(MarketDataEventType.GREEKS, new GreekEventHandler(instrumentAPI));
    }

    public void processBatch(List<MarketDataEvent> events) {
        preProcess(events);
        List<InternalMarketData> validData = new ArrayList<>();

        for (MarketDataEvent event : events) {
            String symbol = event.getSymbol();

            InstrumentData instrumentData;
            try {
                List<InstrumentData> matches = instrumentAPI.lookupInstrumentBySymbol(symbol);
                if (matches.isEmpty()) {
                    System.out.println("Invalid symbol: " + symbol);
                    continue;
                }
                instrumentData = matches.get(0);
            } catch (Exception e) {
                continue;
            }

            // ✅ Informacja o typie eventu (np. do logowania/monitoringu)
            setProcessorType(event.getType().name());

            // ✅ Delegacja do handlera przypisanego do typu eventu
            MarketDataHandler handler = handlerMap.get(event.getType());
            if (handler == null) {
                System.err.println("No handler found for type: " + event.getType());
                continue;
            }

            InternalMarketData processed = handler.handle(event);
            if (processed != null) {
                validData.add(processed);
                PROCESSED_COUNT++;
            }
        }

        sendToSink(validData);

        System.out.println("Total processed: " + PROCESSED_COUNT);
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

    // ✅ Już niepotrzebne: delegacja obsługuje transformację, więc metoda może być pusta lub usunięta
    @Override
    protected List<InternalMarketData> validateAndTransform(List<MarketDataEvent> events) {
        return super.validateAndTransform(events); // zachowujemy, jeśli potrzebne w bazie
    }

    // ✅ Niepotrzebne: handler tworzy obiekty Internal*Data – usuwamy createInternalData
    @Override
    protected InternalMarketData createInternalData(MarketDataEvent event, InstrumentData instrumentData) {
        throw new UnsupportedOperationException("createInternalData should not be used – handled via strategy pattern");
    }
}