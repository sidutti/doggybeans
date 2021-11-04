package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.StockAndQuote;
import com.harish.sidutti.doggybeans.service.YahooFinanceService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MetadataActor extends UntypedAbstractActor {
    private final YahooFinanceService yahooFinanceService;
    private final ActorRef statsMongoActorRef;
    private final ActorRef quoteMongoActorRef;
    public MetadataActor(YahooFinanceService yahooFinanceService, ActorRef statsMongoActorRef, ActorRef quoteMongoActorRef) {
        this.yahooFinanceService = yahooFinanceService;
        this.statsMongoActorRef = statsMongoActorRef;
        this.quoteMongoActorRef = quoteMongoActorRef;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            String value = (String) message;
            StockAndQuote stockAndQuote = yahooFinanceService.processStockData(value.split(" ")[0]);
            statsMongoActorRef.tell(stockAndQuote.getStats(),self());
            quoteMongoActorRef.tell(stockAndQuote.getQuote(),self());
        }
    }
}
