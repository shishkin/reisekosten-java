package domain.services;

@FunctionalInterface
public interface TranslateCitiesToEuCountries {
    boolean isOutsideOfEu(String city);
}
