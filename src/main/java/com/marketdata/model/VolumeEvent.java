package com.marketdata.model;

import com.marketdata.api.model.MarketDataEventType;

public class VolumeEvent extends MarketDataEvent {

    private final Double volume;

    public VolumeEvent(String symbol, String source, Double volume, long timestamp) {
        super(symbol, source, timestamp);
        this.volume = volume;
    }

    public Double getVolume() {
        return volume;
    }

    @Override
    public MarketDataEventType getType() {
        return MarketDataEventType.VOLUME;
    }

    @Override
    public String toString() {
        return "VolumeEvent{" +
                "symbol='" + getSymbol() + '\'' +
                ", volume=" + volume +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}