package com.marketdata.model;

import java.util.Map;

public class GreekEvent extends MarketDataEvent {
    private final Map<String, Double> greeks;
    public GreekEvent(String symbol, String source, Map<String, Double> greeks, long timestamp) {
        super(symbol, source, timestamp);
        this.greeks = greeks;
    }
}
