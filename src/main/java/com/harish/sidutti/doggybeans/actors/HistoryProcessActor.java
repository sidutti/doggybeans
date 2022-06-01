package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.service.YahooFinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.Interval;

import java.util.Calendar;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HistoryProcessActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryProcessActor.class);
    private final YahooFinanceService yahooFinanceService;

    public HistoryProcessActor(YahooFinanceService yahooFinanceService) {
        this.yahooFinanceService = yahooFinanceService;
    }

    @Override
    public void onReceive(Object message) {
        LOGGER.info("Message received on HistoryProcessorActor text={}", message);
        if (message instanceof String) {
            String value = (String) message;
            Calendar from = Calendar.getInstance();
            from.add(Calendar.YEAR, -10);
            yahooFinanceService.processHistoricalResult(value.split("\t")[0], from, Calendar.getInstance(), Interval.DAILY);
        }
    }
}
