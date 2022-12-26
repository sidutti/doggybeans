package com.harish.sidutti.doggybeans.web;

import com.harish.sidutti.Stock;
import com.harish.sidutti.doggybeans.service.*;
import com.harish.sidutti.quotes.stock.StockDividend;
import com.harish.sidutti.quotes.stock.StockQuote;
import com.harish.sidutti.quotes.stock.StockStats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("load")
public class Load {

    private final IndexListService indexListService;
    private final MonthlyHistoryService monthlyHistoryService;
    private final StockService stockService;
    private final StockDividendService stockDividendService;
    private final StockQuoteService stockQuoteService;
    private final StockStatService stockStatService;

    public Load(IndexListService indexListService, MonthlyHistoryService monthlyHistoryService, StockService stockService, StockDividendService stockDividendService, StockQuoteService stockQuoteService, StockStatService stockStatService) {
        this.indexListService = indexListService;
        this.monthlyHistoryService = monthlyHistoryService;
        this.stockService = stockService;
        this.stockDividendService = stockDividendService;
        this.stockQuoteService = stockQuoteService;
        this.stockStatService = stockStatService;
    }


    @GetMapping(value = "file/{fileName:.+}")
    public void loadFile(@PathVariable String fileName) {
        indexListService.loadFile(fileName);
    }

    @GetMapping(value = "stockStat/{stockSymbol}")
    public Mono<StockStats> getStockStat(@PathVariable String stockSymbol) {
        return stockStatService.saveStockStat(stockSymbol);
    }

    @GetMapping(value = "stockQuote/{stockSymbol}")
    public Mono<StockQuote> getStockQuote(@PathVariable String stockSymbol) {
        return stockQuoteService.saveStockQuote(stockSymbol);
    }

    @GetMapping(value = "stockDividend/{stockSymbol}")
    public Mono<StockDividend> getStockDividend(@PathVariable String stockSymbol) {
        return stockDividendService.saveDividend(stockSymbol);
    }

    @GetMapping(value = "stock/{stockSymbol}")
    public Mono<Stock> getStock(@PathVariable String stockSymbol) {
        return stockService.saveStock(stockSymbol);
    }

    @GetMapping(value = "monthlyStock/{stockSymbol}")
    public void getMonthlyQuote(@PathVariable String stockSymbol) {
        monthlyHistoryService.createMonthlyQuotes(stockSymbol);
    }
}
