package com.harish.sidutti.doggybeans.service;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class IndexListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexListService.class);
    private final ActorRef indexHistoryActorRef;
    private final ActorRef stockActorRef;
    private final ActorRef stockDividendActorRef;
    private final ActorRef stockQuoteActorRef;
    private final ActorRef stockStatActorRef;

    public IndexListService(ActorRef indexHistoryActorRef, ActorRef stockActorRef, ActorRef stockDividendActorRef, ActorRef stockQuoteActorRef, ActorRef stockStatActorRef) {
        this.indexHistoryActorRef = indexHistoryActorRef;
        this.stockActorRef = stockActorRef;
        this.stockDividendActorRef = stockDividendActorRef;
        this.stockQuoteActorRef = stockQuoteActorRef;
        this.stockStatActorRef = stockStatActorRef;
    }

    @Override
    public void onReceive(Object message) {
        String fileName = (String) message;
        LOGGER.info("Loading file={}", fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            reader.lines()
                    .map(line -> line.split("\t"))
                    .map(arr -> arr[0])
                    .forEach(stockSymbol -> {
                        indexHistoryActorRef.tell(stockSymbol, self());
                        stockActorRef.tell(stockSymbol, self());
                        stockDividendActorRef.tell(stockSymbol, self());
                        stockQuoteActorRef.tell(stockSymbol, self());
                        stockStatActorRef.tell(stockSymbol, self());
                    });

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
