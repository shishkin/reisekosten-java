package domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Accounting implements Iterable<Travel> {

    final List<Travel> accounting = new ArrayList<>();

    public void enterTravel(LocalDateTime start, LocalDateTime end, SystemClock clock) throws OnlyOneSimultaneousTravelAllowed, TravelExpenseIsTooLate, TravelEndMustOccurAfterEnd {
        travelEndMustOccurAfterStart(start, end);
        travelMustBeEnteredBeforeJan10FollowingYear(end, clock);
        travelsMustNotOverlap(start, end);

        accounting.add(new Travel(start, end));
    }

    private void travelEndMustOccurAfterStart(LocalDateTime start, LocalDateTime end) throws TravelEndMustOccurAfterEnd {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurAfterEnd();
        }
    }


    public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
        if (accounting.stream().anyMatch((travel) -> travel.start().compareTo(start) <= 0 && travel.end().compareTo(end) >= 0)) {
            throw new OnlyOneSimultaneousTravelAllowed();
        }
    }

    public void travelMustBeEnteredBeforeJan10FollowingYear(LocalDateTime end, SystemClock clock) throws TravelExpenseIsTooLate {
        var now = clock.now();
        if (end.getYear() == now.getYear() - 1 && now.getMonthValue() >= Month.JANUARY.getValue() && now.getDayOfMonth() > 10) {
            throw new TravelExpenseIsTooLate();
        }
    }

    @Override
    public Iterator<Travel> iterator() {
        return accounting.iterator();
    }
}
