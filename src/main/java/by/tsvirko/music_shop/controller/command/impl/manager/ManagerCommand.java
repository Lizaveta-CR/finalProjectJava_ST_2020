package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.Role;

public abstract class ManagerCommand extends Command {
    public ManagerCommand() {
        getAllowRoles().add(Role.MANAGER);
    }
}
