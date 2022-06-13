package application.usecases.entry;

import application.AccountingRepository;
import domain.entities.Accounting;
import domain.services.SystemClock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntryTest {
    @Test
    void Should_enter_travel_expense(@Mock SystemClock clock, @Mock AccountingRepository db) {
        var command = new EntryCommand(
                LocalDate.EPOCH.atStartOfDay().atZone(ZoneOffset.UTC),
                LocalDate.EPOCH.atStartOfDay().atZone(ZoneOffset.UTC),
                "destination",
                "reason");
        when(db.loadAccounting()).thenReturn(Mono.just(new Accounting()));
        when(db.saveAccounting(any(Accounting.class))).thenReturn(Mono.empty());
        when(clock.now()).thenReturn(LocalDateTime.MIN);

        var handler = new EntryCommandHandler(clock, db);

        handler.handle(command).block();
    }
}
