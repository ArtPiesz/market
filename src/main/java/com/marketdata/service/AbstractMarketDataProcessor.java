package com.marketdata.service;

import com.marketdata.api.DataSinkAPI;
import com.marketdata.api.InstrumentAPI;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.PriceEvent;
import com.marketdata.model.VolumeEvent;

import java.util.List;

public abstract class AbstractMarketDataProcessor<T extends MarketDataEvent, V> {

    protected InstrumentAPI instrumentAPI;
    protected DataSinkAPI dataSinkAPI;

    public AbstractMarketDataProcessor(InstrumentAPI instrumentAPI, DataSinkAPI dataSinkAPI) {
        this.instrumentAPI = instrumentAPI;
        this.dataSinkAPI = dataSinkAPI;
    }


    protected void preProcess(List<MarketDataEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            MarketDataEvent event = events.get(i);
            if (event instanceof PriceEvent e) {
                if (e.getPrice() == null || e.getPrice() <= 0) {
                    events.remove(i--);
                }
            } else if (event instanceof VolumeEvent v) {
                if (v.getVolume() == null || v.getVolume() <= 0) {
                    events.remove(i--);
                }
            }
        }
    }
}