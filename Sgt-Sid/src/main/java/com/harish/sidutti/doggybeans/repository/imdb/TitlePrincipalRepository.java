package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import com.harish.sidutti.doggybeans.dto.TitlePrincipal;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitlePrincipalRepository  extends ReactiveElasticsearchRepository<TitlePrincipal,String> {
}
