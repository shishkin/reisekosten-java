package domain;

@FunctionalInterface
public interface TranslateCitiesToEuCountries {
    boolean isOutsideOfEu(String city);
}
