package application.integration.usecases;

import application.integration.Testing;
import application.usecases.entry.EntryCommand;
import domain.entities.Accounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class TravelDsl {

    @Autowired
    Testing testing;

    public Mono<Void> enter(String to, String start, String end) {
        var command = new EntryCommand(
                toDateTime(start),
                toDateTime(end),
                to,
                "any");

        return testing.addAsync(new Accounting().withId(42))
                .then(testing.sendAsync(command));
    }

    private LocalDateTime toDateTime(String str) {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(str, format);
        } catch (DateTimeParseException ignored) {
        }

        var parts = str.split(" ");
        var date = switch (parts[0]) {
            case "today" -> LocalDate.now();
            case "tomorrow" -> LocalDate.now().plusDays(1);
            default -> throw new IllegalArgumentException();
        };
        var time = LocalTime.parse(parts[1]);
        return date.atTime(time);
    }
}
