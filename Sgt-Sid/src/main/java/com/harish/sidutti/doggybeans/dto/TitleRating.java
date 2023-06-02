package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titlerating")
public record TitleRating(@Id String tconst, String averageRating, String numVotes) {
    public TitleRating(String[] data){
        this( data[0],data[1],data[2]);
    }
}
