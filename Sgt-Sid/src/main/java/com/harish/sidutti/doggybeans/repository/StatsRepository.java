package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.quotes.stock.StockStats;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends ReactiveElasticsearchRepository<StockStats, String> {
}
