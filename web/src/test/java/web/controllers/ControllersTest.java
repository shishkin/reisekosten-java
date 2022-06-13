package web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import web.TestConfiguration;

@WebFluxTest
@ContextConfiguration(classes = {TestConfiguration.class})
public class ControllersTest {

    @Autowired
    WebTestClient testClient;

    @Test
    void can_get_report() {
        testClient.get()
                .uri("/report")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void can_submit_claim() {
        var response = testClient.post()
                .uri("/submit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "start": "2015-08-06T16:53:10.123+01:00",
                          "end": "2015-08-06T16:53:10.123+01:00",
                          "destination": "Destination",
                          "reason": "Reason"
                        }""")
                .exchange();

        response.expectStatus()
                .is2xxSuccessful();
        response.expectBody().isEmpty();
    }
}
