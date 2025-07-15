package com.marketdata.api.model;

import io.reactivex.rxjava3.annotations.Nullable;

public record InternalPriceData(
        String internalId,
        MarketDataEventType marketDataEventType,
        Double price,
        String source,
        long processedTimestamp,
        @Nullable String warningMessage,
        @Nullable String errorMessage) implements InternalMarketData {
}
