package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.Stock;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveElasticsearchRepository<Stock, String> {
}
