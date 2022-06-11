package application.integration.usecases;

import application.integration.Testing;
import application.usecases.entry.EntryCommand;
import domain.entities.Accounting;
import domain.services.TranslateCitiesToEuCountries;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class EntryAndReportTest extends Testing {

    @Test
    void Should_enter_form_and_create_report() {
        addAsync(new Accounting().withId(42)).block();

        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0);
        var command = new EntryCommand(start, start, "destination", "reason");

        sendAsync(command).block();

        var accounting = loadAsync(42, Accounting.class).block();
        assert accounting != null;

        var report = accounting.report(mock(TranslateCitiesToEuCountries.class));

        assertThat(report).hasSize(1);
    }
}
