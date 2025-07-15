package com.marketdata.model;

import com.marketdata.api.model.MarketDataEventType;

public class PriceEvent extends MarketDataEvent {
    private Double price;

    public PriceEvent(String symbol, String source, Double price, long timestamp) {
        super(symbol, source, timestamp);
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "PriceEvent{" +
                "symbol='" + getSymbol() + '\'' +
                ", price=" + price +
                ", timestamp=" + getTimestamp() +
                '}';
    }

    @Override
    public MarketDataEventType getType() {
        return MarketDataEventType.PRICE;
    }
}
