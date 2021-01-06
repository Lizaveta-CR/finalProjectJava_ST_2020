package by.tsvirko.music_shop.domain.enums;

//TODO:delete
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
