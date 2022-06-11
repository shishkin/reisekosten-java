package application.integration;

import application.AccountingRepository;
import domain.entities.Accounting;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MongoAccountingRepository implements AccountingRepository {

    final ReactiveMongoTemplate template;

    public MongoAccountingRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<Accounting> loadAccounting() {
        return template.findById(42, Accounting.class);
    }

    @Override
    public Mono<Void> saveAccounting(Accounting accounting) {
        return template.save(accounting.withId(42)).then();
    }
}
