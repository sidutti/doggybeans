package com.harish.sidutti.doggybeans.web;

import akka.actor.ActorRef;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("load")
public class Load {

    private final ActorRef metadataActorRef;
    private final ActorRef historyProcessActorRef;

    public Load(ActorRef metadataActorRef, ActorRef historyProcessActorRef) {
        this.metadataActorRef = metadataActorRef;
        this.historyProcessActorRef = historyProcessActorRef;
    }

    @GetMapping(value = "nyse")
    public List<String> loadNYSE() throws IOException {
        Resource resource = new ClassPathResource("NYSE.txt");
        Path nyseData = resource.getFile().toPath();
        return Files.lines(nyseData)
                .peek(str -> metadataActorRef.tell(str, null))
                .peek(str -> historyProcessActorRef.tell(str, null))
                .collect(Collectors.toList());
    }
}
