package by.tsvirko.music_shop.domain.enums;

/**
 * Role enum
 */
public enum Role {
    ADMINISTRATOR("administrator"),
    MANAGER("manager"),
    BUYER("buyer");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getIdentity() {
        return ordinal();
    }

    public static Role getByIdentity(Integer identity) {
        return Role.values()[identity];
    }
}
