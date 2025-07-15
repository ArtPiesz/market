package com.marketdata.api.model;

import io.reactivex.rxjava3.annotations.Nullable;

public interface InternalMarketData {
    String internalId();
    MarketDataEventType marketDataEventType();
    String source();
    long processedTimestamp();
    @Nullable String errorMessage();
    @Nullable String warningMessage();
}
