package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.enums.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
    public AuthorizedUserCommand() {
        getAllowRoles().addAll(Arrays.asList(Role.values()));
    }
}
