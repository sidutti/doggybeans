package com.harish.sidutti.doggybeans.web;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("load")
public class Load {
    private static final Logger LOGGER = LoggerFactory.getLogger(Load.class);
    private final ActorRef metadataActorRef;
    private final ActorRef historyProcessActorRef;

    public Load(ActorRef metadataActorRef, ActorRef historyProcessActorRef) {
        this.metadataActorRef = metadataActorRef;
        this.historyProcessActorRef = historyProcessActorRef;
    }

    @GetMapping(value = "file/{fileName:.+}")
    public void loadNYSE(@PathVariable String fileName) throws IOException {
        LOGGER.info("Loading file={}", fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            reader.lines()
                    .forEach(str -> {
                        metadataActorRef.tell(str, null);
                        historyProcessActorRef.tell(str, null);
                        LOGGER.info(str);
                    });

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
