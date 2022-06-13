package web;

import application.AccountingRepository;
import application.ApplicationConfiguration;
import domain.entities.Accounting;
import domain.services.SystemClock;
import domain.services.TranslateCitiesToEuCountries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import web.controllers.ControllersConfiguration;

import java.time.LocalDateTime;

@Configuration
@Import({ApplicationConfiguration.class, ControllersConfiguration.class})
public class TestConfiguration {

    @Bean
    AccountingRepository inMemoryRepo() {
        return new InMemoryAccountingRepository();
    }

    @Bean
    SystemClock fakeClock() {
        return () -> LocalDateTime.MIN;
    }

    @Bean
    TranslateCitiesToEuCountries alwaysInsideEu() {
        return (_city) -> false;
    }

    @Service
    public static class InitData {
        public InitData(AccountingRepository db) {
            db.saveAccounting(new Accounting()).block();
        }
    }
}
