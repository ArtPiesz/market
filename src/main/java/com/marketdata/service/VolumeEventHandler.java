package com.marketdata.service;

import com.marketdata.api.InstrumentAPI;
import com.marketdata.api.model.InstrumentData;
import com.marketdata.api.model.InternalVolumeData;
import com.marketdata.api.model.MarketDataEventType;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.VolumeEvent;

import java.util.List;
import java.util.Objects;

public class VolumeEventHandler implements MarketDataHandler {

    private final InstrumentAPI instrumentAPI;

    public VolumeEventHandler(InstrumentAPI instrumentAPI) {

        this.instrumentAPI = Objects.requireNonNull(instrumentAPI);
    }

    @Override
    public InternalVolumeData handle(MarketDataEvent event) {

        if (!(event instanceof VolumeEvent volumeEvent)) {

            throw new IllegalArgumentException("Invalid event type passed to VolumeEventHandler: " + event.getClass());
        }


        InstrumentData instrumentData = findInstrument(volumeEvent.getSymbol());


        String internalId = instrumentData != null ? instrumentData.internalId() : null;
        String warning = instrumentData == null ? "Unknown instrument" : null;
        String error = instrumentData == null ? "Instrument not found" : null;

        return new InternalVolumeData(
                internalId,
                MarketDataEventType.VOLUME,
                volumeEvent.getVolume(),
                volumeEvent.getSource(),
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