package by.tsvirko.music_shop.controller.command;

import lombok.Getter;
import lombok.Setter;

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
