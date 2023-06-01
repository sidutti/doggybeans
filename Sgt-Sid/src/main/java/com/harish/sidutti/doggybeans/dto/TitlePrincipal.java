package com.harish.sidutti.doggybeans.dto;

public record TitlePrincipal(String tconst, String ordering, String nconst, String category, String job,
                             String characters) {
    public TitlePrincipal(String[] data){
        this( data[0],data[1],data[2],data[3],data[4],data[5]);
    }
}

