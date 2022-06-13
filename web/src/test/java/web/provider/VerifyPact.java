package web.provider;

import application.AccountingRepository;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import au.com.dius.pact.provider.spring.junit5.WebTestClientTarget;
import domain.entities.Accounting;
import domain.entities.TravelExpenseForm;
import domain.services.SystemClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import web.TestConfiguration;

import java.time.LocalDate;

@WebFluxTest
@ContextConfiguration(classes = {TestConfiguration.class})
@PactBroker
@Provider("Travel Expenses Backend")
public class VerifyPact {

    @Autowired
    WebTestClient testClient;

    @Autowired
    AccountingRepository db;

    @Autowired
    SystemClock clock;

    @BeforeEach
    void setTarget(PactVerificationContext context) {
        context.setTarget(new WebTestClientTarget(testClient));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Accounting without claims")
    void setAccountingWithoutClaims() {
        db.saveAccounting(new Accounting()).block();
    }

    @State("Accounting with 2 claims")
    void setAccountingWithClaims() {
        var accounting = new Accounting();
        accounting.enterTravel(new TravelExpenseForm(
                LocalDate.EPOCH.atStartOfDay(),
                LocalDate.EPOCH.atStartOfDay().plusDays(1),
                "Destination 1",
                "Reason 1"
        ), clock);
        accounting.enterTravel(new TravelExpenseForm(
                LocalDate.EPOCH.atStartOfDay().plusDays(2),
                LocalDate.EPOCH.atStartOfDay().plusDays(3),
                "Destination 2",
                "Reason 2"
        ), clock);
        db.saveAccounting(accounting);
    }
}
