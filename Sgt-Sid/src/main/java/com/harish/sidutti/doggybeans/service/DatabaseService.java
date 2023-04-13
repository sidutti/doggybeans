package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.Stock;
import com.harish.sidutti.doggybeans.repository.*;
import com.harish.sidutti.histquotes.HistoricalQuote;
import com.harish.sidutti.quotes.stock.StockDividend;
import com.harish.sidutti.quotes.stock.StockQuote;
import com.harish.sidutti.quotes.stock.StockStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component

public class DatabaseService {
        private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseService.class);
        private final DividendRepository dividendRepository;
        private final HistoryRepository historyRepository;
        private final QuoteRepository quoteRepository;
        private final StatsRepository statsRepository;
        private final StockRepository stockRepository;

        public DatabaseService(DividendRepository dividendRepository, HistoryRepository historyRepository, QuoteRepository quoteRepository, StatsRepository statsRepository, StockRepository stockRepository) {
                this.dividendRepository = dividendRepository;
                this.historyRepository = historyRepository;
                this.quoteRepository = quoteRepository;
                this.statsRepository = statsRepository;
                this.stockRepository = stockRepository;
        }

        public Mono<Stock> createStock(Stock stocks) {
                return stockRepository.save(stocks);
        }

        public Mono<StockStats> createStats(StockStats stockStats) {
                return statsRepository.save(stockStats);
        }

        public Mono<StockQuote> createQuote(StockQuote stockQuote) {
                return quoteRepository.save(stockQuote);
        }

        public Mono<HistoricalQuote> createHistoricalQuote(HistoricalQuote historicalQuote) {
                return historyRepository.save(historicalQuote);
        }

        public Mono<StockDividend> createDividend(StockDividend stockDividend) {
                return dividendRepository.save(stockDividend);
        }
}
