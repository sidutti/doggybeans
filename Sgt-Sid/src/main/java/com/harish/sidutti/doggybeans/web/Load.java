package com.harish.sidutti.doggybeans.web;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("load")
public class Load {
    private static final Logger LOGGER = LoggerFactory.getLogger(Load.class);
    private final ActorRef indexListActorRef;


    public Load(ActorRef indexListActorRef) {
        this.indexListActorRef = indexListActorRef;
    }

    @GetMapping(value = "file/{fileName:.+}")
    public void loadFile(@PathVariable String fileName) throws IOException {
        indexListActorRef.tell(fileName, null);
    }
}
