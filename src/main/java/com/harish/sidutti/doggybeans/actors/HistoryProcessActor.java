package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.service.YahooFinanceService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.Interval;

import java.util.Calendar;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HistoryProcessActor extends UntypedAbstractActor {
    private final YahooFinanceService yahooFinanceService;

    public HistoryProcessActor(YahooFinanceService yahooFinanceService) {
        this.yahooFinanceService = yahooFinanceService;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            String value = (String) message;
            Calendar from = Calendar.getInstance();
            from.add(Calendar.YEAR, -10);

            yahooFinanceService.processHistoricalResult(value.split(" ")[0], from, Calendar.getInstance(), Interval.DAILY);
        }
    }
}
