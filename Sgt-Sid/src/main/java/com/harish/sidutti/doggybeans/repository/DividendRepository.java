package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.quotes.stock.StockDividend;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends ReactiveElasticsearchRepository<StockDividend, String> {
}
