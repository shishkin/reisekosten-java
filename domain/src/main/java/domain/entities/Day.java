package domain.entities;

import java.time.Duration;
import java.time.LocalDateTime;

public record Day(LocalDateTime start, LocalDateTime end) {
    public Duration duration() {
        return Duration.between(start, end);
    }
}
