package com.marketdata;

import com.marketdata.api.InstrumentAPI;
import com.marketdata.api.MockedStreamingAPI;
import com.marketdata.model.MarketDataEvent;
import com.marketdata.api.DataSinkAPI;
import com.marketdata.service.MarketDataProcessorImpl;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        InstrumentAPI instrumentAPI = new InstrumentAPI();
        DataSinkAPI dataSink = new DataSinkAPI();
        MarketDataProcessorImpl processor = new MarketDataProcessorImpl(instrumentAPI, dataSink);

        System.out.println("Starting market data processing...");

        Disposable subscription = MockedStreamingAPI.createMarketDataStream()
                .subscribe(events -> processEvents(events, processor));

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        subscription.dispose();
        dataSink.shutdown();
        System.out.println("Application terminated");
    }

    private static void processEvents(List<MarketDataEvent> events, MarketDataProcessorImpl processor) {
        System.out.println("Received batch of " + events.size() + " events");
        processor.processBatch(events);
    }
}
