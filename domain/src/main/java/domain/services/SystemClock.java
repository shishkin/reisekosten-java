package domain.services;

import java.time.LocalDateTime;

@FunctionalInterface
public interface SystemClock {
    LocalDateTime now();

    SystemClock Default = LocalDateTime::now;
}
