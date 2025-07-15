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
        registerHandlers(instrumentAPI); // Wydzielenie rejestracji handlerów do osobnej metody poprawia czytelność i separację odpowiedzialności
    }

    private void registerHandlers(InstrumentAPI instrumentAPI) {
        handlerMap.put(MarketDataEventType.PRICE, new PriceEventHandler(instrumentAPI));
        handlerMap.put(MarketDataEventType.VOLUME, new VolumeEventHandler(instrumentAPI));
        handlerMap.put(MarketDataEventType.GREEKS, new GreekEventHandler(instrumentAPI));
    }

    public void processBatch(List<MarketDataEvent> events) {
        preProcess(events);
        List<InternalMarketData> validData = new ArrayList<>();

        for (MarketDataEvent event : events) {
            processSingleEvent(event).ifPresent(validData::add); // Delegacja do osobnej metody redukuje złożoność pętli i poprawia testowalność
        }

        sendToSink(validData);
        System.out.println("Total processed: " + PROCESSED_COUNT);
    }

    private Optional<InternalMarketData> processSingleEvent(MarketDataEvent event) {
        InstrumentData instrumentData = resolveInstrument(event.getSymbol()); // Wydzielenie logiki wyszukiwania instrumentu do osobnej metody poprawia przejrzystość
        if (instrumentData == null) return Optional.empty();



        MarketDataHandler handler = handlerMap.get(event.getType());
        if (handler == null) {
            System.err.println("No handler found for type: " + event.getType());
            return Optional.empty();
        }

        InternalMarketData processed = handler.handle(event);
        if (processed != null) {
            PROCESSED_COUNT++;
            return Optional.of(processed);
        }

        return Optional.empty();
    }

    private InstrumentData resolveInstrument(String symbol) {
        try {
            List<InstrumentData> matches = instrumentAPI.lookupInstrumentBySymbol(symbol);
            if (matches.isEmpty()) {
                System.out.println("Invalid symbol: " + symbol);
                return null;
            }
            return matches.get(0);
        } catch (Exception e) {
            return null;
        }
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




}
