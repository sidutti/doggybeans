package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.Stock;
import com.harish.sidutti.YahooFinance;
import com.harish.sidutti.quotes.stock.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class StockQuoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteService.class);
    private final DatabaseService databaseService;;

    public StockQuoteService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    public Mono<StockQuote> saveStockQuote(String stockSymbol ) {

        try {
            Stock stock = YahooFinance.get(stockSymbol);
            stock.getQuote(true);
            return databaseService.createQuote(stock.getQuote());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return Mono.error(e);
        }
    }
}
