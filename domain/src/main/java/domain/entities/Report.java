package domain.entities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

public record Report(Collection<TravelAllowance> allowances) implements Iterable<TravelAllowance> {

    public BigDecimal totalAllowance() {
        return allowances
                .stream()
                .map(TravelAllowance::allowance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Iterator<TravelAllowance> iterator() {
        return allowances().iterator();
    }
}
