package domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Accounting implements Iterable<Travel> {

    final List<Travel> accounting = new ArrayList<>();

    public void enterTravel(TravelExpenseForm form, SystemClock clock) throws OnlyOneSimultaneousTravelAllowed, TravelExpenseIsTooLate, TravelEndMustOccurAfterEnd {
        travelEndMustOccurAfterStart(form.start(), form.end());
        travelMustBeEnteredBeforeJan10FollowingYear(form.end(), clock);
        travelsMustNotOverlap(form.start(), form.end());

        accounting.add(new Travel(form));
    }

    private void travelEndMustOccurAfterStart(LocalDateTime start, LocalDateTime end) throws TravelEndMustOccurAfterEnd {
        if (end.compareTo(start) < 0) {
            throw new TravelEndMustOccurAfterEnd();
        }
    }


    public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
        if (accounting.stream().anyMatch((travel) -> travel.form().start().compareTo(start) <= 0 && travel.form().end().compareTo(end) >= 0)) {
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
