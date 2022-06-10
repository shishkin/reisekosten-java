package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntryTest {

    Accounting accounting = new Accounting();
    SystemClock defaultClock = () -> LocalDateTime.MIN;

    @BeforeEach
    void setup() {
        accounting = new Accounting();
    }

    @Test
    void Should_accept_travel_with_start_end_destination_and_reason() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        enter(new TravelExpenseForm(start, end, destination, reason), defaultClock);
    }

    @Test
    void Should_reject_travel_with_end_before_start() {
        var start = LocalDateTime.MAX;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        assertThrows(TravelEndMustOccurAfterEnd.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), defaultClock));
    }

    @Test
    void Should_accept_only_one_simultaneous_travel() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MAX.minusYears(2);
        var destination = "any";
        var reason = "any";

        enter(new TravelExpenseForm(start, end, destination, reason), defaultClock);
        assertThrows(OnlyOneSimultaneousTravelAllowed.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), defaultClock));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            Start,               End,                 Now
            2021-12-31T00:00:00, 2021-12-31T00:00:00, 2022-01-11T00:00:00
            1900-01-01T00:00:00, 1900-01-01T00:00:00, 2022-01-11T00:00:00
            1900-01-01T00:00:00, 1900-01-01T00:00:00, 2022-01-01T00:00:00
            """)
    void Should_reject_late_entered_travels(ArgumentsAccessor args) {
        var start = args.get(0, LocalDateTime.class);
        var end = args.get(1, LocalDateTime.class);
        var now = args.get(2, LocalDateTime.class);
        var destination = "any";
        var reason = "any";
        SystemClock clock = () -> now;

        assertThrows(TravelExpenseIsTooLate.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), clock));
    }

    @Test
    void Should_reject_2021_travel_after_Jan_11_2022() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(2021, Month.DECEMBER, 31, 0, 0);
        var destination = "any";
        var reason = "any";
        SystemClock jan11Clock = () -> LocalDateTime.of(2022, Month.JANUARY, 11, 0, 0);

        assertThrows(TravelExpenseIsTooLate.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), jan11Clock));
    }

    @Test
    void Should_reject_1900_travel_on_Jan_11_2022() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0);
        var destination = "any";
        var reason = "any";
        SystemClock jan11Clock = () -> LocalDateTime.of(2022, Month.JANUARY, 11, 0, 0);

        assertThrows(TravelExpenseIsTooLate.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), jan11Clock));
    }

    @Test
    void Should_reject_1900_travel_on_Jan_1_2022() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0);
        var destination = "any";
        var reason = "any";
        SystemClock jan1Clock = () -> LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0);

        assertThrows(TravelExpenseIsTooLate.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), jan1Clock));
    }
    @Test
    void Should_accept_2021_travel_before_Jan_10_2022() {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(2021, Month.DECEMBER, 31, 0, 0);
        var destination = "any";
        var reason = "any";

        var jan10Clock = mock(SystemClock.class);
        when(jan10Clock.now()).thenReturn(LocalDateTime.of(2022, Month.JANUARY, 10, 0, 0));

        enter(new TravelExpenseForm(start, end, destination, reason), jan10Clock);
    }

    public void enter(TravelExpenseForm form, SystemClock clock) {
        accounting.enterTravel(form, clock);
    }
}
