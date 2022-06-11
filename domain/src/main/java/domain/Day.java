package domain;

import java.time.Duration;
import java.time.LocalDateTime;

record Day(LocalDateTime start, LocalDateTime end) {
    Duration duration() {
        return Duration.between(start, end);
    }
}
