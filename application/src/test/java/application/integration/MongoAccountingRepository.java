package application.integration;

import application.AccountingRepository;
import domain.entities.Accounting;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MongoAccountingRepository implements AccountingRepository {

    int id;

    final ReactiveMongoTemplate template;

    public MongoAccountingRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    public void setAccountingIdForTesting(int id) {
        this.id = id;
    }

    @Override
    public Mono<Accounting> loadAccounting() {
        return template.findById(id, Accounting.class);
    }

    @Override
    public Mono<Void> saveAccounting(Accounting accounting) {
        return template.save(accounting).then();
    }
}
