package infrastructure.persistence;

import application.AccountingRepository;
import domain.entities.Accounting;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveAccountingRepository extends
        ReactiveMongoRepository<Accounting, Integer>,
        AccountingRepository {

    @Override
    default Mono<Accounting> loadAccounting() {
        return findById(42);
    }

    @Override
    default Mono<Void> saveAccounting(Accounting accounting) {
        return save(accounting).then();
    }
}
