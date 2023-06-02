package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titleepisode")
public record TitleEpisode(@Id String tconst, String parentTconst, String seasonNumber, String episodeNumber) {
    public TitleEpisode(String[] data){
        this( data[0],data[1],data[2],data[3]);
    }
}
