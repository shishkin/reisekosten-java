package application.usecases.entry;

import an.awesome.pipelinr.Command;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record EntryCommand(
        LocalDateTime start,
        LocalDateTime end,
        String destination,
        String reason) implements Command<Mono<Void>> {
}
