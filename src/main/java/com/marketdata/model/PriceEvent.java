package com.marketdata.model;

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
}
