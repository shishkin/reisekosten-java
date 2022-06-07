package domain;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        assertThrows(TravelEndMustOccurBeforeStart.class ,() -> enter(start, end, destination, reason));
    }

    @Test
    void Should_accept_only_one_simultaneous_travel() throws Exception {
        var start = LocalDateTime.MIN;
        var end = LocalDateTime.MAX;
        var destination = "any";
        var reason = "any";

        enter(start, end, destination, reason);
        assertThrows(OnlyOneSimultaneousTravelAllowed.class ,() -> enter(start, end, destination, reason));
    }

    public record Travel(LocalDateTime start, LocalDateTime end) {
    }

    static List<Travel> bookkeeping = new ArrayList<>();

    public static void enter(LocalDateTime start, LocalDateTime end, String destination, String reason) throws TravelEndMustOccurBeforeStart, OnlyOneSimultaneousTravelAllowed {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurBeforeStart();
        }

        if (bookkeeping.stream().anyMatch((travel) -> travel.start.compareTo(start) <= 0 && travel.end.compareTo(end) >= 0)) {
            throw new OnlyOneSimultaneousTravelAllowed();
        }

        bookkeeping.add(new Travel(start, end));
    }

    public static class TravelEndMustOccurBeforeStart extends Exception {
    }

    public static class OnlyOneSimultaneousTravelAllowed extends Exception {
    }
}
