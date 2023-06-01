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
    private static final String NAMES_FILE = "/home/sidutti/Downloads/imdbdb/name.basics.tsv";

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
}
