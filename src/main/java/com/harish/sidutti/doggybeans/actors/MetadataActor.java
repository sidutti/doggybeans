package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.service.YahooFinanceService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MetadataActor extends UntypedAbstractActor {
    private final YahooFinanceService yahooFinanceService;

    public MetadataActor(YahooFinanceService yahooFinanceService) {
        this.yahooFinanceService = yahooFinanceService;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            String value = (String) message;
            yahooFinanceService.processStockData(value.split(" ")[0]);
        }
    }
}
