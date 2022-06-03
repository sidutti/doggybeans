package com.harish.sidutti.doggybeans.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.harish.sidutti.Stock;
import com.harish.sidutti.doggybeans.repository.*;
import com.harish.sidutti.histquotes.HistoricalQuote;
import com.harish.sidutti.quotes.stock.StockDividend;
import com.harish.sidutti.quotes.stock.StockQuote;
import com.harish.sidutti.quotes.stock.StockStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DbActor extends AbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbActor.class);
    private final DividendRepository dividendRepository;
    private final HistoryRepository historyRepository;
    private final QuoteRepository quoteRepository;
    private final StatsRepository statsRepository;
    private final StockRepository stockRepository;

    public DbActor(DividendRepository dividendRepository, HistoryRepository historyRepository, QuoteRepository quoteRepository, StatsRepository statsRepository, StockRepository stockRepository) {
        this.dividendRepository = dividendRepository;
        this.historyRepository = historyRepository;
        this.quoteRepository = quoteRepository;
        this.statsRepository = statsRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(StockDividend.class, this::createDividend)
                .match(HistoricalQuote.class, this::createHistoricalQuote)
                .match(StockQuote.class, this::createQuote)
                .match(StockStats.class, this::createStats)
                .match(Stock.class, this::createStock)
                .build();
    }

    private void createStock(Stock stocks) {
        stockRepository.save(stocks).subscribe(stock -> LOGGER.info("Stock:{}", stock));
    }

    private void createStats(StockStats stockStats) {
        statsRepository.save(stockStats).subscribe(stock -> LOGGER.info("StockStats:{}", stock));
    }

    private void createQuote(StockQuote stockQuote) {
        quoteRepository.save(stockQuote).subscribe(stock -> LOGGER.info("StockQuote:{}", stock));
    }

    private void createHistoricalQuote(HistoricalQuote historicalQuote) {
        historyRepository.save(historicalQuote).subscribe(stock -> LOGGER.info("History:{}", stock));
    }

    private void createDividend(StockDividend stockDividend) {
        dividendRepository.save(stockDividend).subscribe(stock -> LOGGER.info("StockQuote:{}", stock));
    }
}
