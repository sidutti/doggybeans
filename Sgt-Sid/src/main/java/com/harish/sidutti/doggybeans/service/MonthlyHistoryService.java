package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.StockWithInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.stream.IntStream;

@Component
public class MonthlyHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyHistoryService.class);

    private final IndexMonthService indexMonthService;

    public MonthlyHistoryService(IndexMonthService indexMonthService) {
        this.indexMonthService = indexMonthService;
    }

    public void createMonthlyQuotes(String stockSymbol) {
        try {
            IntStream.range(1, 49)
                    .mapToObj(this::createMonthRequest)
                    .peek(obj -> obj.setStockSymbol(stockSymbol))
                    .peek(indexMonthService::createHistoricalQuote)
                    .forEach(s->LOGGER.debug("Monthly Service Quote created for {}",s));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
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
