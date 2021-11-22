package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.StockAndQuote;
import com.harish.sidutti.doggybeans.service.YahooFinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MetadataActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataActor.class);
    private final YahooFinanceService yahooFinanceService;
    private final ActorRef statsMongoActorRef;
    private final ActorRef quoteMongoActorRef;
    private final ActorRef stockMongoActorRef;

    public MetadataActor(YahooFinanceService yahooFinanceService, ActorRef statsMongoActorRef, ActorRef quoteMongoActorRef, ActorRef stockMongoActorRef) {
        this.yahooFinanceService = yahooFinanceService;
        this.statsMongoActorRef = statsMongoActorRef;
        this.quoteMongoActorRef = quoteMongoActorRef;
        this.stockMongoActorRef = stockMongoActorRef;
    }

    @Override
    public void onReceive(Object message) {
        LOGGER.info("Message Received on MetadataActor text={}", message);
        if (message instanceof String) {
            String value = (String) message;
            StockAndQuote stockAndQuote = yahooFinanceService.processStockData(value.split(" ")[0]);
            statsMongoActorRef.tell(stockAndQuote.getStats(), self());
            quoteMongoActorRef.tell(stockAndQuote.getQuote(), self());
            stockMongoActorRef.tell(stockAndQuote.getStock(), self());
        }
    }
}
