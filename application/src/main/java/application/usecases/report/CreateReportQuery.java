package application.usecases.report;

import an.awesome.pipelinr.Command;
import domain.entities.Report;
import reactor.core.publisher.Mono;

public record CreateReportQuery() implements Command<Mono<Report>> {
}
