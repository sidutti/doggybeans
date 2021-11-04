package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends ReactiveMongoRepository<Stock, String> {
}
