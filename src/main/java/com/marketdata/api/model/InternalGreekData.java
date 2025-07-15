package com.marketdata.api.model;

import io.reactivex.rxjava3.annotations.Nullable;

public record InternalGreekData(
        String internalId,
        MarketDataEventType marketDataEventType,
        Double delta,
        Double gamma,
        String source,
        long processedTimestamp,
        @Nullable String warningMessage,
        @Nullable String errorMessage) implements InternalMarketData {
}