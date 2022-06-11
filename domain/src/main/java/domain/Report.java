package domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

record Report(Collection<TravelAllowance> allowances) implements Iterable<TravelAllowance> {

    BigDecimal totalAllowance() {
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
