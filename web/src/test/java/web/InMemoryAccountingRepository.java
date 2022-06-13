package web;

import application.AccountingRepository;
import domain.entities.Accounting;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InMemoryAccountingRepository implements AccountingRepository {

    Accounting accounting;

    @Override
    public Mono<Accounting> loadAccounting() {
        return Mono.just(accounting);
    }

    @Override
    public Mono<Void> saveAccounting(Accounting accounting) {
        this.accounting = accounting;
        return Mono.empty();
    }
}
