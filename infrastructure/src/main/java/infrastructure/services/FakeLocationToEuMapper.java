package infrastructure.services;

import domain.services.TranslateCitiesToEuCountries;
import org.springframework.stereotype.Service;

@Service
public class FakeLocationToEuMapper implements TranslateCitiesToEuCountries {
    @Override
    public boolean isOutsideOfEu(String city) {
        return !city.contentEquals("Berlin");
    }
}
