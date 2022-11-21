package com.harish.sidutti.doggybeans.service;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class IndexListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexListService.class);
    private final MonthlyHistoryService monthlyHistoryService;
    private final StockService stockService;
    private final StockDividendService stockDividendService;
    private final StockQuoteService stockQuoteService;
    private final StockStatService  stockStatService;

    public IndexListService(MonthlyHistoryService monthlyHistoryService, StockService stockService, StockDividendService stockDividendService, StockQuoteService stockQuoteService, StockStatService stockStatService) {
        this.monthlyHistoryService = monthlyHistoryService;
        this.stockService = stockService;
        this.stockDividendService = stockDividendService;
        this.stockQuoteService = stockQuoteService;
        this.stockStatService = stockStatService;
    }


    public void loadFile(String fileName ) {
        LOGGER.info("Loading file={}", fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            reader.lines()
                    .map(line -> line.split("\t"))
                    .map(arr -> arr[0])
                    .forEach(stockSymbol -> {
                        monthlyHistoryService.createMonthlyQuotes(stockSymbol);
                        stockActorRef.tell(stockSymbol, self());
                        stockDividendActorRef.tell(stockSymbol, self());
                        stockQuoteActorRef.tell(stockSymbol, self());
                        stockStatActorRef.tell(stockSymbol, self());
                    });

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
