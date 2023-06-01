package com.harish.sidutti.doggybeans.dto;

public record TitleEpisode(String tconst, String parentTconst, String seasonNumber, String episodeNumber) {
    public TitleEpisode(String[] data){
        this( data[0],data[1],data[2],data[3]);
    }
}
