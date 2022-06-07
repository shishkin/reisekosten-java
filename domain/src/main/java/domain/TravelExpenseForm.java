package domain;

import java.time.LocalDateTime;

public record TravelExpenseForm(LocalDateTime start, LocalDateTime end, String destination, String reason) {
}
