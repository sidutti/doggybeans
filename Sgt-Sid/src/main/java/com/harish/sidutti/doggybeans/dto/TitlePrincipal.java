package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titleprincipal")
public record TitlePrincipal(@Id String tconst, String ordering, String nconst, String category, String job,
                             String characters) {
    public TitlePrincipal(String[] data){
        this( data[0],data[1],data[2],data[3],data[4],data[5]);
    }
}

