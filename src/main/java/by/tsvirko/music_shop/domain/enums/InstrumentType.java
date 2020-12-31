package by.tsvirko.music_shop.domain.enums;

public enum InstrumentType {
    GUITAR("guitar"),
    VIOLIN("violin"),
    PIANO("piano");

    private String name;

    private InstrumentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
