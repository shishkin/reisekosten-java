package domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AllowanceTest {

    SystemClock defaultClock = () -> LocalDateTime.MIN;

    @Test
    void Should_give_no_allowance_for_travel_under_8_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(7);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, defaultClock);

        assertThat(accounting.stream()
                .findFirst()
                .map(Travel::allowance))
                .contains(BigDecimal.ZERO);
    }

    @Test
    void Should_give_12_EUR_allowance_for_travel_from_12_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(12);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, defaultClock);

        assertThat(accounting.stream()
                .findFirst()
                .map(Travel::allowance))
                .contains(BigDecimal.valueOf(12));
    }

    @Test
    void Should_give_24_EUR_allowance_for_travel_from_24_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(24);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, defaultClock);

        assertThat(accounting.stream()
                .findFirst()
                .map(Travel::allowance))
                .contains(BigDecimal.valueOf(24));
    }
}
