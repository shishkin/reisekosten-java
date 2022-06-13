package application.usecases.report;

import application.AccountingRepository;
import domain.entities.Accounting;
import domain.services.TranslateCitiesToEuCountries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class CreateReportTest {
    @Test
    void Should_create_empty_report(@Mock AccountingRepository db, @Mock TranslateCitiesToEuCountries geo) {
        var query = new CreateReportQuery();

        when(db.loadAccounting()).thenReturn(Mono.just(new Accounting()));

        var handler = new CreateReportQueryHandler(db, geo);

        var report = handler.handle(query).block();

        assert report != null;
        assertThat(report.totalSum())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}
