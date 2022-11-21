package com.harish.sidutti.doggybeans.web;

import com.harish.sidutti.doggybeans.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@RestController
@RequestMapping("load")
public class Load {
    private static final Logger LOGGER = LoggerFactory.getLogger(Load.class);
    private final WebClient client;
    private final IndexListService indexListService;
    private final MonthlyHistoryService monthlyHistoryService;
    private final StockService stockService;
    private final StockDividendService stockDividendService;
    private final StockQuoteService stockQuoteService;
    private final StockStatService stockStatService;

    public Load(WebClient client, IndexListService indexListService, MonthlyHistoryService monthlyHistoryService, StockService stockService, StockDividendService stockDividendService, StockQuoteService stockQuoteService, StockStatService stockStatService) {
        this.client = client;
        this.indexListService = indexListService;
        this.monthlyHistoryService = monthlyHistoryService;
        this.stockService = stockService;
        this.stockDividendService = stockDividendService;
        this.stockQuoteService = stockQuoteService;
        this.stockStatService = stockStatService;
    }


    @GetMapping(value = "file/{fileName:.+}")
    public void loadFile(@PathVariable String fileName) throws IOException {
        indexListService.loadFile(fileName);
    }

    @GetMapping(value = "stockStat/{stockSymbol}")
    public void getStockStat(String stockSymbol) {

    }
}
