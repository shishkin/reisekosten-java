package application.usecases.entry;

import an.awesome.pipelinr.Command;
import application.AccountingRepository;
import domain.entities.TravelExpenseForm;
import domain.services.SystemClock;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EntryCommandHandler implements Command.Handler<EntryCommand, Mono<Void>> {
    private final SystemClock clock;
    private final AccountingRepository db;

    public EntryCommandHandler(SystemClock clock, AccountingRepository db) {
        this.clock = clock;
        this.db = db;
    }

    @Override
    public Mono<Void> handle(EntryCommand request) {
        var form = new TravelExpenseForm(
                request.start(),
                request.end(),
                request.destination(),
                request.reason());

        return db.loadAccounting()
                .doOnNext(acc -> acc.enterTravel(form, clock))
                .flatMap(db::saveAccounting);
    }
}
