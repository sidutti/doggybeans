package com.harish.sidutti.doggybeans.web;

import akka.actor.ActorRef;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("load")
public class Load {
    private final ResourceLoader resourceLoader;
    private final ActorRef metadataActorRef;
    private final ActorRef historyProcessActorRef;

    public Load(ResourceLoader resourceLoader, ActorRef metadataActorRef, ActorRef historyProcessActorRef) {
        this.resourceLoader = resourceLoader;
        this.metadataActorRef = metadataActorRef;
        this.historyProcessActorRef = historyProcessActorRef;
    }

    @GetMapping(value = "nyse")
    public List<String> loadNYSE() throws IOException {
        Path nyseData = ResourceUtils.getFile("classpath:NYSE.txt").toPath();
        return Files.lines(nyseData)
                .peek(str -> metadataActorRef.tell(str, null))
                .peek(str -> historyProcessActorRef.tell(str, null))
                .collect(Collectors.toList());
    }
}
