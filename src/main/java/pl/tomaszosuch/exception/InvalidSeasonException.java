package pl.tomaszosuch.exception;

public class InvalidSeasonException extends RuntimeException {

    private final String season;

    public InvalidSeasonException(String season) {
        super("Nieznana pora roku: " + season);
        this.season = season;
    }

    public String getSeason() {
        return season;
    }
}
