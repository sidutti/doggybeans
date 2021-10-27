package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Quote;
import com.harish.sidutti.doggybeans.repository.QuoteRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QuoteMongoActor extends UntypedAbstractActor {

    private final QuoteRepository quoteRepository;

    public QuoteMongoActor(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void onReceive(Object message)  {
        if(message instanceof Quote){
            Mono<Quote> updatable = quoteRepository.save((Quote) message);
        }
    }
}
