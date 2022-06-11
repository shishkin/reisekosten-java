package domain;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record Travel(TravelExpenseForm form) {

    BigDecimal allowance(TranslateCitiesToEuCountries geo) {
        return new AllowanceStrategyFactory()
                .resolve(geo, form().destination())
                .calculate(days());
    }

    private Collection<Day> days() {
        List<Day> days = new ArrayList<>();
        var startOfDay = form.start();
        do {
            var endOfDay = startOfDay.plusDays(1).truncatedTo(ChronoUnit.DAYS);

            if (endOfDay.isAfter(form.end())) {
                endOfDay = form.end();
            }

            days.add(new Day(startOfDay, endOfDay));
            startOfDay = endOfDay;
        } while (startOfDay.isBefore(form.end()));
        return days;
    }
}
