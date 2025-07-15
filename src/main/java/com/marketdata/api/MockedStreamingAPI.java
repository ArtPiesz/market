package com.marketdata.api;

import com.marketdata.model.GreekEvent;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.model.PriceEvent;
import com.marketdata.model.VolumeEvent;
import io.reactivex.rxjava3.core.Flowable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MockedStreamingAPI {
    private static final Random random = new Random();
    private static final List<String> symbols = Arrays.asList(
            "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "FB", "NFLX", "NVDA", "PYPL", "ADBE",
            "INTC", "CSCO", "CMCSA", "PEP", "AVGO", "TXN", "QCOM", "TMUS", "AMD", "CHTR", "TEST"
    );

    private static final List<String> sources = Arrays.asList("API", "EXCHANGE");

    private static final List<String> marketDataEventTypes = Arrays.asList("PRICE", "VOLUME","GREEK");//, "GREEK");

    public static Flowable<List<MarketDataEvent>> createMarketDataStream() {
        return Flowable.interval(50, TimeUnit.MILLISECONDS)
                .map(tick -> generateBatch(5 + random.nextInt(10)))
                .onBackpressureDrop();
    }

    private static List<MarketDataEvent> generateBatch(int size) {
        MarketDataEvent[] events = new MarketDataEvent[size];
        for (int i = 0; i < size; i++) {
            String symbol = symbols.get(random.nextInt(symbols.size()));
            String source = sources.get(random.nextInt(sources.size()));
            String eventType = marketDataEventTypes.get(random.nextInt(marketDataEventTypes.size()));
            boolean nullEvent = random.nextBoolean();
            boolean negativeValue = random.nextInt(10) < 2; // 20% chance for negative values

            if ("PRICE".equals(eventType)) {
                Double price = nullEvent ? null : (100 + random.nextDouble() * 900) * (negativeValue ? -1 : 1);
                events[i] = new PriceEvent(symbol, source, price, System.currentTimeMillis());
            } else if ("VOLUME".equals(eventType)) {
                Double volume = nullEvent ? null : ((double) 1000 + random.nextInt(10000)) * (negativeValue ? -1 : 1);
                events[i] = new VolumeEvent(symbol, source, volume, System.currentTimeMillis());
            } else if ("GREEK".equals(eventType)) {
                Map<String, Double> greeks = new HashMap<>(2);
                if (!nullEvent) {
                    greeks.put("delta", random.nextDouble() * (negativeValue ? -1 : 1));
                    greeks.put("gamma", random.nextDouble() * (negativeValue ? -1 : 1));
                }
                events[i] = new GreekEvent(symbol, source, greeks, System.currentTimeMillis());
            }
        }
        return new ArrayList<>(Arrays.asList(events));
    }
}
