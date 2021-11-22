package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Stock;
import com.harish.sidutti.doggybeans.repository.StocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockMongoActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockMongoActor.class);
    private final StocksRepository stocksRepository;

    public StockMongoActor(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Stock) {
            LOGGER.info("Message Received on StockMongoActor text={}", message);
            Mono<Stock> updatable = stocksRepository.save((Stock) message);
            updatable.subscribe(e -> LOGGER.info(e.toString()));
        }
    }
}
