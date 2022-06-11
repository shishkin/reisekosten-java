package application.integration.usecases;

import application.integration.Testing;
import application.usecases.report.CreateReportQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ReportDsl {

    @Autowired
    Testing testing;

    public Mono<Void> withNumberOfEntries(int number) {
        var query = new CreateReportQuery();

        return testing.sendAsync(query)
                .doOnNext(report -> assertThat(report).hasSize(number))
                .then();
    }

    public Mono<Void> withTotalSum(BigDecimal sum) {
        var query = new CreateReportQuery();

        return testing.sendAsync(query)
                .doOnNext(report ->
                        assertThat(report.totalAllowance())
                                .isEqualByComparingTo(sum))
                .then();
    }
}
