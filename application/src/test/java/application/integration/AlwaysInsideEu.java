package application.integration;

import domain.services.TranslateCitiesToEuCountries;
import org.springframework.stereotype.Service;

@Service
public class AlwaysInsideEu implements TranslateCitiesToEuCountries {
    @Override
    public boolean isOutsideOfEu(String city) {
        return false;
    }
}
