package com.marketdata.service;

import com.marketdata.api.InstrumentAPI;
import com.marketdata.api.model.InstrumentData;
import com.marketdata.api.model.InternalGreekData;
import com.marketdata.api.model.MarketDataEventType;
import com.marketdata.model.GreekEvent;
import com.marketdata.model.MarketDataEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GreekEventHandler implements MarketDataHandler {

    private final InstrumentAPI instrumentAPI;

    public GreekEventHandler(InstrumentAPI instrumentAPI) {

        this.instrumentAPI = Objects.requireNonNull(instrumentAPI);
    }

    @Override
    public InternalGreekData handle(MarketDataEvent event) {

        if (!(event instanceof GreekEvent greekEvent)) {

            throw new IllegalArgumentException("Invalid event type passed to GreekEventHandler: " + event.getClass());
        }


        InstrumentData instrumentData = findInstrument(greekEvent.getSymbol());


        String internalId = instrumentData != null ? instrumentData.internalId() : null;
        String warning = instrumentData == null ? "Unknown instrument" : null;
        String error = instrumentData == null ? "Instrument not found" : null;


        Map<String, Double> greeks = greekEvent.getGreeks();
        Double delta = greeks != null ? greeks.get("delta") : null;
        Double gamma = greeks != null ? greeks.get("gamma") : null;

        return new InternalGreekData(
                internalId,
                MarketDataEventType.GREEKS,
                delta,
                gamma,
                greekEvent.getSource(),
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