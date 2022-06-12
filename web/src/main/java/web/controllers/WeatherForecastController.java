package web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import web.WeatherForecast;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/")
public class WeatherForecastController {
    static final String[] summaries = new String[]{
            "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };

    @GetMapping
    private Flux<WeatherForecast> get() {
        return Flux.fromStream(IntStream.range(1, 5)
                .mapToObj(index -> new WeatherForecast(
                        LocalDate.now().plusDays(index),
                        ThreadLocalRandom.current().nextInt(-20, 55),
                        summaries[ThreadLocalRandom.current().nextInt(summaries.length)]
                )));
    }
}
