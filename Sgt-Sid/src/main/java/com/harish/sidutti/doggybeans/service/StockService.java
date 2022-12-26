package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.Stock;
import com.harish.sidutti.YahooFinance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component

public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDividendService.class);
    private final DatabaseService databaseService;

    public StockService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Mono<Stock> saveStock(String stockSymbol) {
        try {
            Stock stock = YahooFinance.get(stockSymbol);
            if (stock != null) {
                stock.getDividend(true);
                return databaseService.createStock(stock);
            }
            return Mono.empty();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return Mono.error(e);
        }
    }

}