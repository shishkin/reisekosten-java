package domain.allowances;

import domain.services.TranslateCitiesToEuCountries;

public class AllowanceStrategyFactory {
    public AllowanceStrategy resolve(TranslateCitiesToEuCountries geo, String destination) {
        if (geo.isOutsideOfEu(destination)) {
            return new AllowanceOutsideEu();
        }

        return new AllowanceInsideEu();
    }
}
