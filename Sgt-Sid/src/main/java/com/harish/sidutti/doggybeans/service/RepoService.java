package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.*;
import com.harish.sidutti.doggybeans.repository.imdb.*;
import org.springframework.stereotype.Component;

@Component
public class RepoService {
    private final NamesRepository namesRepository;
    private final TitleCrewRepository titleCrewRepository;
    private final TitleEpisodeRepository titleEpisodeRepository;
    private final TitlePrincipalRepository titlePrincipalRepository;
    private final TitleRatingRepository titleRatingRepository;
    private final TitlesAkaRepository titlesAkaRepository;
    private final TitlesRepository titlesRepository;

    public RepoService(NamesRepository namesRepository, TitleCrewRepository titleCrewRepository, TitleEpisodeRepository titleEpisodeRepository, TitlePrincipalRepository titlePrincipalRepository, TitleRatingRepository titleRatingRepository, TitlesAkaRepository titlesAkaRepository, TitlesRepository titlesRepository) {
        this.namesRepository = namesRepository;
        this.titleCrewRepository = titleCrewRepository;
        this.titleEpisodeRepository = titleEpisodeRepository;
        this.titlePrincipalRepository = titlePrincipalRepository;
        this.titleRatingRepository = titleRatingRepository;
        this.titlesAkaRepository = titlesAkaRepository;
        this.titlesRepository = titlesRepository;
    }

    public void saveRecord(Record pojoToSave) {
        if (pojoToSave instanceof Names) {
            namesRepository
                    .save((Names) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof TitleCrews) {
            titleCrewRepository
                    .save((TitleCrews) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof TitleEpisode) {
            titleEpisodeRepository
                    .save((TitleEpisode) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof TitlePrincipal) {
            titlePrincipalRepository
                    .save((TitlePrincipal) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof TitleRating) {
            titleRatingRepository
                    .save((TitleRating) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof TitlesAka) {
            titlesAkaRepository
                    .save((TitlesAka) pojoToSave)
                    .subscribe();
        }
        if (pojoToSave instanceof Titles) {
            titlesRepository
                    .save((Titles) pojoToSave)
                    .subscribe();
        }

    }
}
