package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.enums.Role;

/**
 * Admin command abstract class
 */
public abstract class AdminCommand extends Command {
    /**
     * Class constructor. Only users with Role.ADMINISTRATOR can access it's inheritors
     */
    public AdminCommand() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
