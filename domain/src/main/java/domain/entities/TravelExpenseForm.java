package domain.entities;

import domain.errors.TravelEndMustOccurAfterEnd;

import java.time.LocalDateTime;

public record TravelExpenseForm(LocalDateTime start, LocalDateTime end, String destination, String reason) {
    public TravelExpenseForm {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurAfterEnd();
        }
    }
}
