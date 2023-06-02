package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titles")
public record Titles(@Id String tconst, String titleType, String primaryTitle, String originalTitle, String isAdult,
                     String startYear, String endYear, String runtimeMinutes, String genres) {
    public Titles(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
    }
}
