package domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Accounting implements Iterable<Travel> {

    final List<Travel> accounting = new ArrayList<>();

    public void enterTravel(TravelExpenseForm form, SystemClock clock) {
        travelMustBeEnteredBeforeJan10FollowingYear(form.end(), clock);
        travelsMustNotOverlap(form.start(), form.end());

        accounting.add(new Travel(form));
    }

    public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) {
        if (accounting.stream().anyMatch((travel) -> travel.form().start().compareTo(start) <= 0 && travel.form().end().compareTo(end) >= 0)) {
            throw new OnlyOneSimultaneousTravelAllowed();
        }
    }

    public void travelMustBeEnteredBeforeJan10FollowingYear(LocalDateTime end, SystemClock clock) {
        var validUntil = LocalDateTime.of(end.plusYears(1).getYear(), Month.JANUARY, 10, 23, 59, 59);
        if (clock.now().compareTo(validUntil) > 0) {
            throw new TravelExpenseIsTooLate();
        }
    }

    @Override
    public Iterator<Travel> iterator() {
        return accounting.iterator();
    }
}
