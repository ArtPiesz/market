package com.marketdata.model;

import com.marketdata.api.model.MarketDataEventType;

import java.util.Map;

public class GreekEvent extends MarketDataEvent {
    private final Map<String, Double> greeks;
    public GreekEvent(String symbol, String source, Map<String, Double> greeks, long timestamp) {
        super(symbol, source, timestamp);
        this.greeks = greeks;
    }

    public Map<String, Double> getGreeks() {
        return greeks;
    }

    @Override
    public MarketDataEventType getType() {
        return MarketDataEventType.GREEKS;
    }
}
