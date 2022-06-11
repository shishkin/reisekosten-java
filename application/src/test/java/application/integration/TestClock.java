package application.integration;

import domain.services.SystemClock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TestClock implements SystemClock {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(2022, 1, 1, 0, 0);
    }
}
