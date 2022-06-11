package application;

import domain.entities.Accounting;
import reactor.core.publisher.Mono;

public interface AccountingRepository {
    Mono<Accounting> loadAccounting();

    Mono<Void> saveAccounting(Accounting accounting);
}
