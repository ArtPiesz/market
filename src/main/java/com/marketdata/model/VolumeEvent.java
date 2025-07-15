package com.marketdata.model;

public class VolumeEvent extends MarketDataEvent {
    private Double volume;

    public VolumeEvent(String symbol, String source, Double volume, long timestamp) {
        super(symbol, source, timestamp);
        this.volume = volume;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "VolumeEvent{" +
                "symbol='" + getSymbol() + '\'' +
                ", volume=" + volume +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
