package com.harish.sidutti.doggybeans.repository.imdb;

import com.harish.sidutti.doggybeans.dto.Names;
import com.harish.sidutti.doggybeans.dto.TitleRating;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRatingRepository  extends ReactiveElasticsearchRepository<TitleRating,String> {
}
