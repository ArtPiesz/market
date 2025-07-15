package com.marketdata.api.model;

import io.reactivex.rxjava3.annotations.Nullable;

public record InternalVolumeData(
        String internalId,
        MarketDataEventType marketDataEventType,
        Double volume,
        String source,
        long processedTimestamp,
        @Nullable String warningMessage,
        @Nullable String errorMessage) implements InternalMarketData {
}
