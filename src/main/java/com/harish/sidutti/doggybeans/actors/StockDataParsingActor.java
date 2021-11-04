package com.harish.sidutti.doggybeans.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockDataParsingActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger("StockDataParsingActor");
    private final ActorRef historyMongoActorRef;

    public StockDataParsingActor(ActorRef historyMongoActorRef) {
        this.historyMongoActorRef = historyMongoActorRef;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            String messageLine = (String) message;
            History history = processMessageLine(messageLine);
            historyMongoActorRef.tell(history, self());
        } else {
            unhandled(message);
        }

    }

    private History processMessageLine(String line) {
        try {
            String[] data = line.split(YahooFinance.QUOTES_CSV_DELIMITER);
            return new History(data[7],
                    data[0],
                    Utils.getBigDecimal(data[1]),
                    Utils.getBigDecimal(data[3]),
                    Utils.getBigDecimal(data[2]),
                    Utils.getBigDecimal(data[4]),
                    Utils.getBigDecimal(data[5]),
                    Utils.getLong(data[6])
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
