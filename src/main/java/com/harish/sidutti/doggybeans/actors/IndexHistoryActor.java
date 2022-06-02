package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.StockWithInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.stream.IntStream;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexHistoryActor extends UntypedAbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexHistoryActor.class);

    private final ActorRef indexMonthActorRef;

    public IndexHistoryActor(ActorRef indexMonthActorRef) {
        this.indexMonthActorRef = indexMonthActorRef;
    }

    @Override
    public void onReceive(Object message) {
        try {
            String stockSymbol = (String) message;
            IntStream.range(1, 49)
                    .mapToObj(this::createMonthRequest)
                    .peek(obj -> obj.setStockSymbol(stockSymbol))
                    .forEach(res -> indexMonthActorRef.tell(res, self()));
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
