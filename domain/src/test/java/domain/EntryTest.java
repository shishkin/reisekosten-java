package domain;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntryTest {

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

    final Accounting accounting = new Accounting();

    public record Travel(LocalDateTime start, LocalDateTime end) {
    }

    public static class Accounting extends ArrayList<Travel> {
        public void enterTravel(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
            travelsMustNotOverlap(start, end);

            this.add(new Travel(start, end));
        }

        public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
            if (this.stream().anyMatch((travel) -> travel.start.compareTo(start) <= 0 && travel.end.compareTo(end) >= 0)) {
                throw new OnlyOneSimultaneousTravelAllowed();
            }
        }
    }

    public void enter(LocalDateTime start, LocalDateTime end, String destination, String reason) throws TravelEndMustOccurBeforeStart, OnlyOneSimultaneousTravelAllowed {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurBeforeStart();
        }

        accounting.enterTravel(start, end);
    }

    public static class TravelEndMustOccurBeforeStart extends Exception {
    }

    public static class OnlyOneSimultaneousTravelAllowed extends Exception {
    }
}
