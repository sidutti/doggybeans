package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.Stock;
import com.harish.sidutti.histquotes.HistoricalQuote;
import com.harish.sidutti.quotes.stock.StockDividend;
import com.harish.sidutti.quotes.stock.StockQuote;
import com.harish.sidutti.quotes.stock.StockStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class IndexListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexListService.class);
    private final WebClient client;
    private final String uri;

    public IndexListService(WebClient client, @Value("app.service.name") String uri) {
        this.client = client;
        this.uri = uri;
    }


    public void loadFile(String fileName) {
        LOGGER.info("Loading file={}", fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            reader.lines()
                    .map(line -> line.split("\t"))
                    .map(arr -> arr[0])
                    .peek(this::createStock)
                    .peek(this::createStockStat)
                    .peek(this::createStockQuote)
                    .peek(this::createDividend)
                    .peek(this::createMonthlyQuote)
                    .forEach(LOGGER::debug);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Mono<Stock> createStock(String stockSymbol) {
        return client.method(HttpMethod.GET)
                .uri(uri + "/load/stock/" + stockSymbol)
                .retrieve()
                .bodyToMono(Stock.class);
    }
    private Mono<StockDividend> createDividend(String stockSymbol) {
        return client.method(HttpMethod.GET)
                .uri(uri + "/load/stockDividend/" + stockSymbol)
                .retrieve()
                .bodyToMono(StockDividend.class);
    }
    private Mono<StockQuote> createStockQuote(String stockSymbol) {
        return client.method(HttpMethod.GET)
                .uri(uri + "/load/stockQuote/" + stockSymbol)
                .retrieve()
                .bodyToMono(StockQuote.class);
    }
    private Mono<StockStats> createStockStat(String stockSymbol) {
        return client.method(HttpMethod.GET)
                .uri(uri + "/load/stockStat/" + stockSymbol)
                .retrieve()
                .bodyToMono(StockStats.class);
    }
    private Mono<HistoricalQuote> createMonthlyQuote(String stockSymbol) {
        return client.method(HttpMethod.GET)
                .uri(uri + "/load/monthlyStock/" + stockSymbol)
                .retrieve()
                .bodyToMono(HistoricalQuote.class);
    }
}
