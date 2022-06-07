package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntryTest {

    Accounting accounting = new Accounting();

    @BeforeEach
    void setup(){
        accounting = new Accounting();
    }

    @Test
    void Should_accept_travel_with_start_end_destination_and_reason() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        enter(start, end, destination, reason);
    }

    @Test
    void Should_reject_travel_with_end_before_start() throws Exception {
        var start = LocalDateTime.MAX;
        var end = LocalDateTime.MIN;
        var destination = "any";
        var reason = "any";

        assertThrows(TravelEndMustOccurBeforeStart.class, () -> enter(start, end, destination, reason));
    }

    @Test
    void Should_accept_only_one_simultaneous_travel() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MAX;
        var destination = "any";
        var reason = "any";

        enter(start, end, destination, reason);
        assertThrows(OnlyOneSimultaneousTravelAllowed.class, () -> enter(start, end, destination, reason));
    }

    public void enter(LocalDateTime start, LocalDateTime end, String destination, String reason) throws TravelEndMustOccurBeforeStart, OnlyOneSimultaneousTravelAllowed {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurBeforeStart();
        }

        accounting.enterTravel(start, end);
    }
}
