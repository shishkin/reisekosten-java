package domain;

import domain.entities.TravelExpenseForm;
import domain.errors.TravelEndMustOccurAfterEnd;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TravelExpenseFormTest {
    @Test
    void Should_reject_end_before_start() {
        assertThrows(TravelEndMustOccurAfterEnd.class, () -> new TravelExpenseForm(LocalDateTime.MAX, LocalDateTime.MIN, "any", "any"));
    }
}
