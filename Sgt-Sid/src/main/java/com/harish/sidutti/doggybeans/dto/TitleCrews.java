package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titlecrew")
public record TitleCrews(@Id String tconst, String directors, String writers) {
    public TitleCrews(String[] data){
        this( data[0],data[1],data[2]);
    }
}

