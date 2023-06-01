package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import com.harish.sidutti.doggybeans.dto.TitlesAka;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitlesAkaRepository  extends ReactiveElasticsearchRepository<TitlesAka,String> {
}
