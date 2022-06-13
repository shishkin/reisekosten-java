package web.controllers;

import an.awesome.pipelinr.Pipeline;
import application.usecases.entry.EntryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/submit")
public class SubmitClaimController {

    @Autowired
    Pipeline pipeline;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> submit(@RequestBody EntryCommand command) {
        return pipeline.send(command);
    }
}
