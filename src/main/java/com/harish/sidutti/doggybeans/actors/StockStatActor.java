package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.Stock;
import com.harish.sidutti.YahooFinance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockStatActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockStatActor.class);
    private final ActorRef dbActorRef;

    public StockStatActor(ActorRef dbActorRef) {
        this.dbActorRef = dbActorRef;
    }

    @Override
    public void onReceive(Object message) {
        String stockSymbol = (String) message;
        try {
            Stock stock = YahooFinance.get(stockSymbol);
            stock.getStats(true);
            dbActorRef.tell(stock.getStats(), self());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
