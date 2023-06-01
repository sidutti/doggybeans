package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamesRepository extends ReactiveElasticsearchRepository<Names,String> {
}
