package application.usecases.report;

import an.awesome.pipelinr.Command;
import reactor.core.publisher.Mono;

public record CreateReportQuery() implements Command<Mono<ReportViewModel>> {
}
