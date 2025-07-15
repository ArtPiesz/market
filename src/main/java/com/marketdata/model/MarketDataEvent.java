package com.marketdata.model;

public abstract class MarketDataEvent {
    private String symbol;
    private String source;
    private long timestamp;

    public MarketDataEvent(String symbol, String source, long timestamp) {
        this.symbol = symbol;
        this.source = source;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInternalId() {
        return symbol;
    }

    public void setInternalId(String symbol) {
        this.symbol = symbol;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }

    public abstract com.marketdata.api.model.MarketDataEventType getType();
}
