package domain;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import domain.entities.Accounting;
import domain.entities.Report;
import domain.entities.TravelAllowance;
import domain.entities.TravelExpenseForm;
import domain.services.SystemClock;
import domain.services.TranslateCitiesToEuCountries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SnapshotExtension.class})
public class AllowanceTest implements TranslateCitiesToEuCountries, SystemClock {

    @Test
    void Should_give_no_allowance_for_travel_under_8_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(7);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, this);

        var report = calculate(accounting, this);
        assertThat(report.totalAllowance())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void Should_give_12_EUR_allowance_for_travel_from_12_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(12);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, this);

        var report = calculate(accounting, this);
        assertThat(report.totalAllowance())
                .isEqualByComparingTo(BigDecimal.valueOf(12));
    }

    @Test
    void Should_give_24_EUR_allowance_for_travel_from_24_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(24);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, this);

        var report = calculate(accounting, this);
        assertThat(report.totalAllowance())
                .isEqualByComparingTo(BigDecimal.valueOf(24));
    }

    @Test
    void Should_calculate_allowance_for_multi_day_travel() {
        var start = LocalDateTime.of(2022, 1, 1, 16, 0);
        var end = LocalDateTime.of(2022, 1, 3, 14, 0);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, this);

        var report = calculate(accounting, this);
        assertThat(report.totalAllowance())
                .isEqualByComparingTo(BigDecimal.valueOf(6 + 24 + 12));
    }

    @Test
    void Should_include_travel_details_in_report(Expect expect) {
        var travel1 = new TravelExpenseForm(
                LocalDateTime.MIN,
                LocalDateTime.MIN.plusDays(1),
                "Berlin",
                "Republica");
        var travel2 = new TravelExpenseForm(
                LocalDateTime.MIN.plusDays(1),
                LocalDateTime.MIN.plusDays(2),
                "Leipzig",
                "Developer Open Space");

        var accounting = new Accounting();
        accounting.enterTravel(travel1, this);
        accounting.enterTravel(travel2, this);

        var report = calculate(accounting, this);

        assertThat(report).hasSize(2);
        assertThat(report
                .allowances()
                .stream()
                .findFirst()
                .map(TravelAllowance::destination)
        ).contains("Berlin");
        assertThat(report
                .allowances()
                .stream()
                .findFirst()
                .map(TravelAllowance::reason)
        ).contains("Republica");

        expect
                .serializer("json")
                .toMatchSnapshot(report);
    }

    @Test
    void Should_calculate_allowance_of_100_EUR_for_travel_outside_EU() {
        var start = LocalDateTime.MIN;
        var end = start;
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, this);

        TranslateCitiesToEuCountries geo = city -> true;

        var report = calculate(accounting, geo);

        assertThat(report.totalAllowance()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    static Report calculate(Accounting accounting, TranslateCitiesToEuCountries geo) {
        return accounting.report(geo);
    }

    @Override
    public boolean isOutsideOfEu(String city) {
        return false;
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.MIN;
    }
}
