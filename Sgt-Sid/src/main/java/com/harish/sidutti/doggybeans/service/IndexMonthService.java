package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.StockWithInterval;
import com.harish.sidutti.histquotes.Interval;
import com.harish.sidutti.histquotes2.HistQuotes2Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class IndexMonthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexMonthService.class);
    private final DatabaseService databaseService;

    public IndexMonthService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    public void createHistoricalQuote(StockWithInterval stockSymbol) {
        HistQuotes2Request request = new HistQuotes2Request(stockSymbol.getStockSymbol(), stockSymbol.getFrom(), stockSymbol.getTo(), Interval.DAILY);
        try {
            request.getBufferedReader()
                    .lines()
                    .map(request::parseCSVLine)
                    .map(databaseService::createHistoricalQuote)
                    .map(Mono::subscribe)
                    .forEach(s->LOGGER.debug("Monthly Service request created for {}",s));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
