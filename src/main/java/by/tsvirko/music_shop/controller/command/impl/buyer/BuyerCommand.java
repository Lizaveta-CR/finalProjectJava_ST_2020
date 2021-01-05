package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.domain.enums.Role;

public abstract class BuyerCommand extends Command {
    public BuyerCommand() {
        getAllowRoles().add(Role.BUYER);
    }
}
