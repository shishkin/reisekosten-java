package application.usecases.entry;

import an.awesome.pipelinr.Command;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

public record EntryCommand(
        ZonedDateTime start,
        ZonedDateTime end,
        String destination,
        String reason) implements Command<Mono<Void>> {
}
