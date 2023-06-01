package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.*;
import org.springframework.stereotype.Component;

@Component
public class PojoService {
    public Record createPojo(String name, String data){
        if(name.equalsIgnoreCase("names")){
           return new Names( data.split("\t"));
        } else if(name.equalsIgnoreCase("titleCrews")){
            return new TitleCrews( data.split("\t"));
        } else if (name.equalsIgnoreCase("titleepisode")) {
            return new TitleEpisode( data.split("\t"));
        } else if (name.equalsIgnoreCase("titleprincipal")) {
            return new TitlePrincipal( data.split("\t"));
        } else if (name.equalsIgnoreCase("titlerating")) {
            return new TitleRating( data.split("\t"));
        } else if (name.equalsIgnoreCase("titles")) {
            return new Titles( data.split("\t"));
        } else if (name.equalsIgnoreCase("titlesaka")) {
            return new TitlesAka( data.split("\t"));
        }
        return null;
    }
}
