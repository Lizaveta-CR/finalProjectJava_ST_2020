package by.tsvirko.music_shop.controller.command.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Main page menu. Is used when user logs in or registers
 */
@Getter
@Setter
public class Menu {
    private String url;
    private String name;

    public Menu(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Menu(String url) {
        this.url = url;
    }
}
