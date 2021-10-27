package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Quote;
import com.harish.sidutti.doggybeans.dto.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
}
