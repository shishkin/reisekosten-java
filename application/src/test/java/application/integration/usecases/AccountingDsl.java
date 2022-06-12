package application.integration.usecases;

import application.integration.Testing;
import domain.entities.Accounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class AccountingDsl {

    @Autowired
    Testing testing;

    public Mono<Void> exists() {
        var id = ThreadLocalRandom.current().nextInt();
        testing.setAccountingIdForTesting(id);
        return testing.addAsync(new Accounting().withId(id));
    }
}
