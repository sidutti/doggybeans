package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import com.harish.sidutti.doggybeans.dto.TitleCrews;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleCrewRepository  extends ReactiveElasticsearchRepository<TitleCrews,String> {
}
