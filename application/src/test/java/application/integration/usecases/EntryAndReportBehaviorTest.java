package application.integration.usecases;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class EntryAndReportBehaviorTest extends Behavior {

    @Test
    void Should_enter_travel_and_create_report() {
        travel.enter("Leipzig", "today 16:00", "tomorrow 20:00").block();

        report.withNumberOfEntries(1).block();
        report.withTotalSum(BigDecimal.valueOf(18)).block();
    }
}
