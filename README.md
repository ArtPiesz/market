# Market Data Processor Extension and Refactoring Challenge

## Setup Instructions

This project contains a Java application that processes market data through a streaming API. Your task is to refactor the code while maintaining similar functionality.

### Running the Project

1. Clone this repository
2. Run the application using:
```
./gradlew run
```
(Use `gradlew.bat run` on Windows)

If you cannot run the project using the above command (can for example happen if you are behind a company proxy), please follow these steps:
1. Download [gradle-8.10.2-all.zip](https://services.gradle.org/distributions/gradle-8.10.2-all.zip) and place it in the repository root
2. Update the Gradle wrapper configuration:
    - Open `gradle/wrapper/gradle-wrapper.properties`
    - Change the `distributionUrl` to: `file:///<absolute path to this project>/gradle-8.10.2-all.zip`
    - For example: `file:///C:/projects/market-data-processor/gradle-8.10.2-all.zip`

## Challenge Description

### Background

You've been handed a Java application that processes market data events from a streaming source. The application:
1. Receives batches of market data events (prices and volumes) from a mocked streaming API
2. Validates the events by checking instrument information via an instrument API
3. Transforms the events into an internal data model
4. Sends the processed data to a sink

### Problem Statement

The code has numerous design and implementation issues that make it difficult to maintain and extend.

### Your Task

Extend the market data processor application to also handle greek processing (delta and gamma).
While adding this third market data type, refactor the codebase to address the code and design issues while maintaining the functionality.

### Constraints

- Do not modify the API classes (`InstrumentAPI`, `InstrumentData`, `MockedStreamingAPI` (except for allowing greeks to be generated))
- Do not change the input/output behavior of the application
- The application should still process price and volume events after adding greek processing
- The application should continue sending data to the provided `DataSink`

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── marketdata/
│               ├── Main.java                        // Application entry point
│               ├── api/                             // API interfaces and implementations 
│               │   ├── DataSinkAPI.java             // Output destination API
│               │   ├── InstrumentAPI.java           // API for instrument lookups
│               │   ├── MockedStreamingAPI.java      // Simulated market data stream
│               │   └── model/                       // API data models
│               │       ├── InstrumentData.java      // Instrument data model
│               │       ├── InternalMarketData.java  // Internal market data interface
│               │       ├── InternalPriceData.java   // Internal price data model
│               │       ├── InternalVolumeData.java  // Internal volume data model
│               │       └── MarketDataEventType.java // Event type enum
│               ├── model/                           // Event models
│               │   ├── GreekEvent.java              // Greek-specific event
│               │   ├── MarketDataEvent.java         // Base event class
│               │   ├── PriceEvent.java              // Price-specific event
│               │   └── VolumeEvent.java             // Volume-specific event
│               └── service/                         // Processing components
│                   ├── AbstractMarketDataProcessor.java  // Base processor class
│                   └── MarketDataProcessorImpl.java      // Processor implementation
```

## Evaluation Criteria

Your coding and refactoring will be evaluated based on:

1. **Code Quality**: Clean, readable, and maintainable code
2. **Design Patterns**: Appropriate use of OO design patterns
3. **Performance**: Efficient processing of market data
4. **Error Handling**: Robust and transparent handling of errors and edge cases
5. **Functionality**: Maintaining the main functionality of the application while adding support for greek processing

## Expected Outcome
Extended market data processor with greek processing support, with a refactored codebase that:
- Processes market data events correctly
- Has clean, maintainable code and architecture
- Uses appropriate abstractions and patterns
- Handles errors
- Performs efficiently

Good luck!
