package web.controllers;

import an.awesome.pipelinr.Pipeline;
import application.usecases.report.CreateReportQuery;
import application.usecases.report.ReportViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    Pipeline pipeline;

    @GetMapping
    public Mono<ReportViewModel> get() {
        return pipeline.send(new CreateReportQuery());
    }
}
