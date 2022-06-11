package domain;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SnapshotExtension.class})
public class AllowanceTest {

    SystemClock defaultClock = () -> LocalDateTime.MIN;

    @Test
    void Should_give_no_allowance_for_travel_under_8_hours() {
        var start = LocalDateTime.MIN;
        var end = start.plusHours(7);
        var destination = "any";
        var reason = "any";
        var form = new TravelExpenseForm(start, end, destination, reason);

        var accounting = new Accounting();
        accounting.enterTravel(form, defaultClock);

        var report = calculate(accounting);
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
        accounting.enterTravel(form, defaultClock);

        var report = calculate(accounting);
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
        accounting.enterTravel(form, defaultClock);

        var report = calculate(accounting);
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
        accounting.enterTravel(form, defaultClock);

        var report = calculate(accounting);
        assertThat(report.totalAllowance())
                .isEqualByComparingTo(BigDecimal.valueOf(6 + 24 + 12));
    }

    @Test
    public void Should_include_travel_details_in_report(Expect expect) {
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
        accounting.enterTravel(travel1, defaultClock);
        accounting.enterTravel(travel2, defaultClock);

        var report = calculate(accounting);

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

    static Report calculate(Accounting accounting) {
        return accounting.report();
    }
}
