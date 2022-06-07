package domain;

import java.time.LocalDateTime;

@FunctionalInterface
interface SystemClock {
    LocalDateTime now();

    SystemClock Default = LocalDateTime::now;
}
