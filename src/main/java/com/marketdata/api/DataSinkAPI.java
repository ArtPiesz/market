package com.marketdata.api;

import com.marketdata.api.model.InternalMarketData;

public class DataSinkAPI {

    public <T extends Number> void publishData(InternalMarketData data) {
        // In a real application, this would publish to a message broker or database
        System.out.println("Publishing to sink: " + data);
    }

    public void shutdown() {
        System.out.println("Shutting down data sink");
    }
}
