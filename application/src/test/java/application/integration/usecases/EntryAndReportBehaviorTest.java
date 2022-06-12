package application.integration.usecases;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class EntryAndReportBehaviorTest extends Behavior {

    @Test
    void Should_enter_travel_and_create_report() {
        accounting.exists().block();

        travel.enter("Leipzig", "today 16:00", "tomorrow 20:00").block();

        report.withNumberOfEntries(1).block();
        report.withTotalSum(BigDecimal.valueOf(18)).block();
    }

    @Test
    void Should_enter_multiple_travels_and_create_report() {
        accounting.exists().block();

        travel.enter("Leipzig", "today 16:00", "tomorrow 20:00").block();
        travel.enter("Hamburg", "2022-02-01 16:00", "2022-02-02 20:00").block();

        report.withNumberOfEntries(2).block();
        report.withTotalSum(BigDecimal.valueOf(36)).block();
    }
}
