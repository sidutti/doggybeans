package com.harish.sidutti.doggybeans.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="titleaka")
public record TitlesAka(@Id String titleId, String ordering, String title, String region, String language, String types,
                        String attributes, String isOriginalTitle) {
    public TitlesAka(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
    }
}
