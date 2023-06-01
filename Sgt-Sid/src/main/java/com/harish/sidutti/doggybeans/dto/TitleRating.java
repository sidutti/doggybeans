package com.harish.sidutti.doggybeans.dto;

public record TitleRating(String tconst, String averageRating, String numVotes) {
    public TitleRating(String[] data){
        this( data[0],data[1],data[2]);
    }
}
