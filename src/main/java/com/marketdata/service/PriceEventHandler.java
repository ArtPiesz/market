package com.marketdata.service;

import com.marketdata.api.InstrumentAPI;
import com.marketdata.api.model.InstrumentData;
import com.marketdata.api.model.InternalPriceData;
import com.marketdata.api.model.MarketDataEventType;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.PriceEvent;

import java.util.List;
import java.util.Objects;

public class PriceEventHandler implements MarketDataHandler {

    private final InstrumentAPI instrumentAPI;

    public PriceEventHandler(InstrumentAPI instrumentAPI) {

        this.instrumentAPI = Objects.requireNonNull(instrumentAPI);
    }

    @Override
    public InternalPriceData handle(MarketDataEvent event) {

        if (!(event instanceof PriceEvent priceEvent)) {

            throw new IllegalArgumentException("Invalid event type passed to PriceEventHandler: " + event.getClass());
        }


        InstrumentData instrumentData = findInstrument(priceEvent.getSymbol());


        String internalId = instrumentData != null ? instrumentData.internalId() : null;
        String warning = instrumentData == null ? "Unknown instrument" : null;
        String error = instrumentData == null ? "Instrument not found" : null;

        return new InternalPriceData(
                internalId,
                MarketDataEventType.PRICE,
                priceEvent.getPrice(),
                priceEvent.getSource(),
                System.currentTimeMillis(),
                warning,
                error
        );
    }


    private InstrumentData findInstrument(String symbol) {
        List<InstrumentData> matches = instrumentAPI.lookupInstrumentBySymbol(symbol);
        return matches.isEmpty() ? null : matches.get(0);
    }


}