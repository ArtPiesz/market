package com.marketdata.model;

import com.marketdata.api.model.MarketDataEventType;

public abstract class MarketDataEvent {

    private final String symbol;   // symbol giełdowy, np. "AAPL"
    private final String source;   // źródło danych, np. "API", "EXCHANGE"
    private final long timestamp;  // znacznik czasu eventu

    public MarketDataEvent(String symbol, String source, long timestamp) {
        if (symbol == null || source == null) {
            throw new IllegalArgumentException("Symbol and source must not be null");
        }
        this.symbol = symbol;
        this.source = source;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSource() {
        return source;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // każdy event implementuje ten getter jednoznacznie
    public abstract MarketDataEventType getType();
}