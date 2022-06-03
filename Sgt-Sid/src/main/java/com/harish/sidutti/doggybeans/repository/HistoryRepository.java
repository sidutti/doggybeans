package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.histquotes.HistoricalQuote;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends ReactiveElasticsearchRepository<HistoricalQuote, String> {
}
