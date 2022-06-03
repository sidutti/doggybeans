package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.StockWithInterval;
import com.harish.sidutti.histquotes.Interval;
import com.harish.sidutti.histquotes2.HistQuotes2Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexMonthActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexMonthActor.class);
    private final ActorRef dbActorRef;

    public IndexMonthActor(ActorRef dbActorRef) {
        this.dbActorRef = dbActorRef;
    }

    @Override
    public void onReceive(Object message) {
        StockWithInterval stockSymbol = (StockWithInterval) message;
        HistQuotes2Request request = new HistQuotes2Request(stockSymbol.getStockSymbol(), stockSymbol.getFrom(), stockSymbol.getTo(), Interval.DAILY);
        try {
            request.getBufferedReader()
                    .lines()
                    .map(request::parseCSVLine)
                    .forEach(hq -> dbActorRef.tell(hq, self()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
