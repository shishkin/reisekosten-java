package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntryTest {

    Accounting accounting = new Accounting();
    SystemClock defaultClock = SystemClock.Default;

    @BeforeEach
    void setup() {
        accounting = new Accounting();
    }

    @Test
    void Should_accept_travel_with_start_end_destination_and_reason() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        enter(new TravelExpenseForm(start, end, destination, reason), defaultClock);
    }

    @Test
    void Should_reject_travel_with_end_before_start() throws Exception {
        var start = LocalDateTime.MAX;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        assertThrows(TravelEndMustOccurAfterEnd.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), defaultClock));
    }

    @Test
    void Should_accept_only_one_simultaneous_travel() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MAX;
        var destination = "any";
        var reason = "any";

        enter(new TravelExpenseForm(start, end, destination, reason), defaultClock);
        assertThrows(OnlyOneSimultaneousTravelAllowed.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), defaultClock));
    }

    @Test
    void Should_reject_2021_travel_after_Jan_11_2022() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(2021, Month.DECEMBER, 31, 0, 0);
        var destination = "any";
        var reason = "any";
        SystemClock jan11Clock = () -> LocalDateTime.of(2022, Month.JANUARY, 11, 0, 0);

        assertThrows(TravelExpenseIsTooLate.class, () -> enter(new TravelExpenseForm(start, end, destination, reason), jan11Clock));
    }

    @Test
    void Should_accept_2021_travel_before_Jan_10_2022() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.of(2021, Month.DECEMBER, 31, 0, 0);
        var destination = "any";
        var reason = "any";

        var jan10Clock = mock(SystemClock.class);
        when(jan10Clock.now()).thenReturn(LocalDateTime.of(2022, Month.JANUARY, 10, 0, 0));

        enter(new TravelExpenseForm(start, end, destination, reason), jan10Clock);
    }

    public void enter(TravelExpenseForm form, SystemClock clock) throws TravelEndMustOccurAfterEnd, OnlyOneSimultaneousTravelAllowed, TravelExpenseIsTooLate {
        accounting.enterTravel(form, clock);
    }
}
