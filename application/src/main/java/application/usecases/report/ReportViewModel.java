package application.usecases.report;

import java.math.BigDecimal;
import java.util.Collection;

public record ReportViewModel(
        BigDecimal totalSum,
        Collection<TravelViewModel> travels) {
}
