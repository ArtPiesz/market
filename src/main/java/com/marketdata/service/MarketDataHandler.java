package com.marketdata.service;

import com.marketdata.api.model.MarketDataEventType;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.api.model.InternalMarketData;

public interface MarketDataHandler {
    /**
     * raw input -> InternalMarketData.
     *
     * @param event raw input
     * @return internal representation ready to datasink or null if not procesable
     */
    InternalMarketData handle(MarketDataEvent event);


}