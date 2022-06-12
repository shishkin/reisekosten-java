package web;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.LocalDate;

public record WeatherForecast(LocalDate date, int temperatureC, String summary) {

    @JsonGetter
    int temperatureF() {
        return 32 + (int) (temperatureC / 0.5556);
    }
}
