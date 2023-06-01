package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import com.harish.sidutti.doggybeans.dto.TitleEpisode;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleEpisodeRepository  extends ReactiveElasticsearchRepository<TitleEpisode,String> {
}
