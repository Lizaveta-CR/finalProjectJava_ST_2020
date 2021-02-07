package by.tsvirko.music_shop.controller.command.impl.common.authorizedUser;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
    public AuthorizedUserCommand() {
        getAllowRoles().addAll(Arrays.asList(Role.values()));
    }
}
