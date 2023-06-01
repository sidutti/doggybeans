package com.harish.sidutti.doggybeans.dto;

public record Names(String nconst, String primaryName, String birthYear, String deathYear, String primaryProfession,
                    String knownForTitles) {
    public Names(String[] data){
        this( data[0],data[1],data[2],data[3],data[4],data[5]);
    }
}

