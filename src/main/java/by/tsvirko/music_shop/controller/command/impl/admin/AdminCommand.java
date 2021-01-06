package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.enums.Role;

public abstract class AdminCommand extends Command {
    public AdminCommand() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
