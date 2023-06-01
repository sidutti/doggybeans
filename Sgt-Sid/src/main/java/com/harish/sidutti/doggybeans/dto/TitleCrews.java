package com.harish.sidutti.doggybeans.dto;

public record TitleCrews(String tconst, String directors, String writers) {
    public TitleCrews(String[] data){
        this( data[0],data[1],data[2]);
    }
}

