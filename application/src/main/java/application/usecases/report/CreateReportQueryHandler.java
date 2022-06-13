package application.usecases.report;

import an.awesome.pipelinr.Command;
import application.AccountingRepository;
import domain.services.TranslateCitiesToEuCountries;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateReportQueryHandler implements Command.Handler<CreateReportQuery, Mono<ReportViewModel>> {
    private final AccountingRepository db;
    private final TranslateCitiesToEuCountries geo;

    public CreateReportQueryHandler(AccountingRepository db, TranslateCitiesToEuCountries geo) {
        this.db = db;
        this.geo = geo;
    }

    @Override
    public Mono<ReportViewModel> handle(CreateReportQuery request) {
        return db.loadAccounting()
                .map(accounting -> accounting.report(geo))
                .map(report -> new ReportViewModel(
                        report.totalAllowance(),
                        report.allowances().stream()
                                .map(a -> new TravelViewModel(
                                        a.start(),
                                        a.end(),
                                        a.reason(),
                                        a.destination(),
                                        a.allowance()))
                                .toList()));
    }
}
