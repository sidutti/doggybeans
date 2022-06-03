package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.quotes.stock.StockQuote;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends ReactiveElasticsearchRepository<StockQuote, String> {
}
