package com.harish.sidutti.doggybeans.web;

import com.harish.sidutti.doggybeans.service.PojoService;
import com.harish.sidutti.doggybeans.service.RepoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("imdb")
public class IMDBController {
    private final PojoService service;
    private final RepoService repoService;
    private static final String NAMES_FILE = "/home/sidutti/Downloads/imdbdb/name.basics.tsv/data.tsv";
    private static final String TITLES_FILE = "/home/sidutti/Downloads/imdbdb/title.basics.tsv/data.tsv";
    private static final String TITLES_PRINCIPAL_FILE = "/home/sidutti/Downloads/imdbdb/title.principals.tsv/data.tsv";
    private static final String TITLES_EPISODE_FILE = "/home/sidutti/Downloads/imdbdb/title.episode.tsv/data.tsv";
    private static final String TITLES_AKA_FILE = "/home/sidutti/Downloads/imdbdb/title.akas.tsv/data.tsv";
    private static final String TITLES_CREW_FILE = "/home/sidutti/Downloads/imdbdb/title.crew.tsv/data.tsv";

    private static final String TITLES_RATING_FILE = "/home/sidutti/Downloads/imdbdb/title.ratings.tsv/data.tsv";

    public IMDBController(PojoService service, RepoService repoService) {
        this.service = service;
        this.repoService = repoService;
    }

    @GetMapping("names")
    public String loadFiles() throws IOException {
        Files.lines(Paths.get(NAMES_FILE))
                .parallel()
                .map(str -> service.createPojo("names", str))
                .forEach(repoService::saveRecord);
        return NAMES_FILE;
    }
    @GetMapping("title")
    public String loadTitleFiles() throws IOException {
        Files.lines(Paths.get(TITLES_FILE))
                .parallel()
                .map(str -> service.createPojo("title", str))
                .forEach(repoService::saveRecord);
        return TITLES_FILE;
    }
    @GetMapping("titlePrincipal")
    public String loadTitlePrincipalFiles() throws IOException {
        Files.lines(Paths.get(TITLES_PRINCIPAL_FILE))
                .parallel()
                .map(str -> service.createPojo("titlePrincipal", str))
                .forEach(repoService::saveRecord);
        return TITLES_PRINCIPAL_FILE;
    }
    @GetMapping("titleEpisode")
    public String loadTitleEpisodeFiles() throws IOException {
        Files.lines(Paths.get(TITLES_EPISODE_FILE))
                .parallel()
                .map(str -> service.createPojo("titleEpisode", str))
                .forEach(repoService::saveRecord);
        return TITLES_EPISODE_FILE;
    }
    @GetMapping("titleAka")
    public String loadTitleAkaFiles() throws IOException {
        Files.lines(Paths.get(TITLES_AKA_FILE))
                .parallel()
                .map(str -> service.createPojo("titleAka", str))
                .forEach(repoService::saveRecord);
        return TITLES_AKA_FILE;
    }
    @GetMapping("titleCrew")
    public String loadTitleCrewFiles() throws IOException {
        Files.lines(Paths.get(TITLES_CREW_FILE))
                .parallel()
                .map(str -> service.createPojo("titleCrew", str))
                .forEach(repoService::saveRecord);
        return TITLES_CREW_FILE;
    }
    @GetMapping("titleRatings")
    public String loadTitleRatingsFiles() throws IOException {
        Files.lines(Paths.get(TITLES_RATING_FILE))
                .parallel()
                .map(str -> service.createPojo("titleRatings", str))
                .forEach(repoService::saveRecord);
        return TITLES_RATING_FILE;
    }
}
