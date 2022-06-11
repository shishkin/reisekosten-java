package domain.entities;

import domain.errors.OnlyOneSimultaneousTravelAllowed;
import domain.errors.TravelExpenseIsTooLate;
import domain.services.SystemClock;
import domain.services.TranslateCitiesToEuCountries;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Accounting {

    Integer id = 0;

    List<Travel> travels = new ArrayList<>();

    public Accounting withId(Integer id) {
        this.id = id;
        return this;
    }

    public Accounting withTravels(List<Travel> travels) {
        this.travels = travels;
        return this;
    }

    public void enterTravel(TravelExpenseForm form, SystemClock clock) {
        travelMustBeEnteredBeforeJan10FollowingYear(form.end(), clock);
        travelsMustNotOverlap(form.start(), form.end());

        travels.add(new Travel(form));
    }

    public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) {
        if (travels.stream().anyMatch((travel) -> travel.form().start().compareTo(start) <= 0 && travel.form().end().compareTo(end) >= 0)) {
            throw new OnlyOneSimultaneousTravelAllowed();
        }
    }

    public void travelMustBeEnteredBeforeJan10FollowingYear(LocalDateTime end, SystemClock clock) {
        var validUntil = LocalDateTime.of(end.plusYears(1).getYear(), Month.JANUARY, 10, 23, 59, 59);
        if (clock.now().compareTo(validUntil) > 0) {
            throw new TravelExpenseIsTooLate();
        }
    }

    public Stream<Travel> stream() {
        return travels.stream();
    }

    public Report report(TranslateCitiesToEuCountries geo) {
        var allowances = stream()
                .map(travel -> new TravelAllowance(
                        travel.form().start(),
                        travel.form().end(),
                        travel.form().destination(),
                        travel.form().reason(),
                        travel.allowance(geo)))
                .toList();
        return new Report(allowances);
    }
}
