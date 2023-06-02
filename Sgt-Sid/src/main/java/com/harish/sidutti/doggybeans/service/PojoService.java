package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.*;
import org.springframework.stereotype.Component;

@Component
public class PojoService {
    public Record createPojo(String name, String data){
        if(name.equalsIgnoreCase("names")){
           return new Names( data.split("\t"));
        } else if(name.equalsIgnoreCase("titleCrew")){
            return new TitleCrews( data.split("\t"));
        } else if (name.equalsIgnoreCase("titleEpisode")) {
            return new TitleEpisode( data.split("\t"));
        } else if (name.equalsIgnoreCase("titlePrincipal")) {
            return new TitlePrincipal( data.split("\t"));
        } else if (name.equalsIgnoreCase("titleRatings")) {
            return new TitleRating( data.split("\t"));
        } else if (name.equalsIgnoreCase("titles")) {
            return new Titles( data.split("\t"));
        } else if (name.equalsIgnoreCase("titleAka")) {
            return new TitlesAka( data.split("\t"));
        }
        return null;
    }
}
