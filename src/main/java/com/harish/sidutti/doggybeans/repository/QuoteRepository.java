package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Quote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends ReactiveMongoRepository<Quote, String> {
}
