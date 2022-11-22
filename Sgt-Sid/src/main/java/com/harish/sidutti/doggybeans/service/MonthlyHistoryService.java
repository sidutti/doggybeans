package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.StockWithInterval;
import com.harish.sidutti.histquotes.HistoricalQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MonthlyHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyHistoryService.class);

    private final IndexMonthService indexMonthService;

    public MonthlyHistoryService(IndexMonthService indexMonthService) {
        this.indexMonthService = indexMonthService;
    }
    public Flux<Mono<HistoricalQuote>> createMonthlyQuotes(String stockSymbol ) {
        try {
            return Flux.fromIterable(IntStream.range(1, 49)
                    .mapToObj(this::createMonthRequest)
                    .peek(obj -> obj.setStockSymbol(stockSymbol))
                    .map(indexMonthService::createHistoricalQuote)
                    .flatMap(Flux::toStream)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private StockWithInterval createMonthRequest(int i) {
        Calendar from = Calendar.getInstance();
        from.add(Calendar.MONTH, (i - 1) * -1);
        Calendar to = Calendar.getInstance();
        to.add(Calendar.MONTH, i * -1);
        StockWithInterval result = new StockWithInterval();
        result.setFrom(from);
        result.setTo(to);
        return result;
    }
}
