package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.Stock;
import com.harish.sidutti.YahooFinance;
import com.harish.sidutti.quotes.stock.StockStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class StockStatService {
        private static final Logger LOGGER = LoggerFactory.getLogger(StockStatService.class);
        private final DatabaseService databaseService;

        public StockStatService(DatabaseService databaseService) {
                this.databaseService = databaseService;
        }

        public Mono<StockStats> saveStockStat(String stockSymbol) {
                try {
                        Stock stock = YahooFinance.get(stockSymbol);
                        if (stock != null) {
                                stock.getStats(true);
                                return databaseService.createStats(stock.getStats());
                        }
                        return Mono.empty();
                } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        return Mono.error(e);
                }
        }
}
