package com.marketdata.api;

import com.marketdata.api.model.InstrumentData;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class InstrumentAPI {
    private static final Map<String, InstrumentData> instruments = new HashMap<>();
    private static final Map<String, InstrumentData> instrumentsByInternalId = new HashMap<>();

    static {
        instruments.put("AAPL", new InstrumentData("AAPL","AAPL_NRP_INT", "Apple Inc.", "EQUITY"));
        instruments.put("MSFT", new InstrumentData("MSFT","MSFT_NRP_INT", "Microsoft Corp", "EQUITY"));
        instruments.put("GOOGL", new InstrumentData("GOOGL","GOOGL_NRP_INT", "Alphabet Inc.", "EQUITY"));
        instruments.put("AMZN", new InstrumentData("AMZN","AMZN_NRP_INT", "Amazon.com Inc.", "EQUITY"));
        instruments.put("TSLA", new InstrumentData("TSLA","TSLA_NRP_INT", "Tesla Inc.", "EQUITY"));
        instruments.put("FB", new InstrumentData("FB","FB_NRP_INT", "Meta Platforms Inc.", "EQUITY"));
        instruments.put("NFLX", new InstrumentData("NFLX","NFLX_NRP_INT", "Netflix Inc.", "EQUITY"));
        instruments.put("NVDA", new InstrumentData("NVDA","NVDA_NRP_INT", "NVIDIA Corp", "EQUITY"));
        instruments.put("PYPL", new InstrumentData("PYPL","PYPL_NRP_INT", "PayPal Holdings Inc.", "EQUITY"));
        instruments.put("ADBE", new InstrumentData("ADBE","ADBE_NRP_INT", "Adobe Inc.", "EQUITY"));
        instruments.put("INTC", new InstrumentData("INTC","INTC_NRP_INT", "Intel Corp", "EQUITY"));
        instruments.put("CSCO", new InstrumentData("CSCO","CSCO_NRP_INT", "Cisco Systems Inc.", "EQUITY"));
        instruments.put("CMCSA", new InstrumentData("CMCSA","CMCSA_NRP_INT", "Comcast Corp", "EQUITY"));
        instruments.put("PEP", new InstrumentData("PEP","PEP_NRP_INT", "PepsiCo Inc.", "EQUITY"));
        instruments.put("AVGO", new InstrumentData("AVGO","AVGO_NRP_INT", "Broadcom Inc.", "EQUITY"));
        instruments.put("TXN", new InstrumentData("TXN","TXN_NRP_INT", "Texas Instruments Inc.", "EQUITY"));
        instruments.put("QCOM", new InstrumentData("QCOM","QCOM_NRP_INT", "Qualcomm Inc.", "EQUITY"));
        instruments.put("TMUS", new InstrumentData("TMUS","TMUS_NRP_INT", "T-Mobile US Inc.", "EQUITY"));
        instruments.put("AMD", new InstrumentData("AMD","AMD_NRP_INT", "Advanced Micro Devices Inc.", "EQUITY"));
        instruments.put("CHTR", new InstrumentData("CHTR","CHTR_NRP_INT", "Charter Communications Inc.", "EQUITY"));
        instruments.values().forEach(id -> instrumentsByInternalId.put(id.internalId(), id));
    }

    public List<InstrumentData> lookupInstrumentByInternalId(String internalId) {
        return lookupInstrumentByInternalId(List.of(internalId));
    }
    public List<InstrumentData> lookupInstrumentByInternalId(List<String> internalId) {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return internalId.stream().map(instrumentsByInternalId::get).filter(Objects::nonNull).toList();
    }

    public List<InstrumentData> lookupInstrumentBySymbol(String symbol) {
        return lookupInstrumentBySymbol(List.of(symbol));
    }

    public List<InstrumentData> lookupInstrumentBySymbol(List<String> symbol) {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return symbol.stream().map(instruments::get).filter(Objects::nonNull).toList();
    }
}
