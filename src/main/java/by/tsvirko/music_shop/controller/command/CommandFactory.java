package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.impl.admin.*;
import by.tsvirko.music_shop.controller.command.impl.buyer.*;
import by.tsvirko.music_shop.controller.command.impl.common.*;
import by.tsvirko.music_shop.controller.command.impl.manager.*;

/**
 * Command factory class.
 */
public class CommandFactory {
    private CommandFactory() {
    }

    private static class Holder {
        private static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return CommandFactory.Holder.INSTANCE;
    }

    /**
     * Gets commands by name.
     *
     * @param name - command name
     * @return Command corresponding to name
     */
    private static Command getCommandByName(CommandName name) {
        switch (name) {
            //common commands
            case ABOUT_VIEW_COMMAND:
                return new AboutViewCommand();
            case CATEGORIES_COMMAND:
                return new CategoriesCommand();
            case CHANGE_LANGUAGE_COMMAND:
                return new ChangeLanguageCommand();
            case LOGIN_COMMAND:
                return new LoginCommand();
            case LOGOUT_COMMAND:
                return new LogoutCommand();
            case MAIN_COMMAND:
                return new MainCommand();
            case REGISTER_COMMAND:
                return new RegisterCommand();
            case SHOW_PRODUCER_PRODUCTS_COMMAND:
                return new ShowProducerProductsCommand();
            case VIEW_REGISTER_COMMAND:
                return new ViewRegisterCommand();
            case VIEW_LOGIN_COMMAND:
                return new ViewLoginCommand();
            //buyer commands
            case BUYER_ADD_PRODUCT_COMMAND:
                return new BuyerAddProductCommand();
            case BUYER_EDIT_COMMAND:
                return new BuyerEditCommand();
            case BUYER_EDIT_PASS_COMMAND:
                return new BuyerEditPassCommand();
            case BUYER_FORM_COMMAND:
                return new BuyerFormCommand();
            case BUYER_PRODUCTS_BY_RATE_COMMAND:
                return new BuyerProductsByRateCommand();
            case BUYER_REMOVE_PRODUCT_COMMAND:
                return new BuyerRemoveProductCommand();
            case BUYER_VIEW_EDIT_FORM_COMMAND:
                return new BuyerViewEditFormCommand();
            case BUYER_VIEW_EDIT_PASS_FORM_COMMAND:
                return new BuyerViewEditPassFormCommand();
            case BUYER_VIEW_SUBMIT_ORDER_COMMAND:
                return new BuyerViewSubmitOrderCommand();
            case EDIT_ADDRESS_COMMAND:
                return new EditAddressCommand();
            case SUBMIT_FEEDBACK_COMMAND:
                return new SubmitFeedBackCommand();
            case SUBMIT_ORDER_COMMAND:
                return new SubmitOrderCommand();
            case VIEW_ADDRESS_COMMAND:
                return new ViewAddressCommand();
            case VIEW_FEEDBACK_PAGE:
                return new ViewFeedBackPage();
            case VIEW_ORDER_COMMAND:
                return new ViewOrderCommand();
            //admin commands
            case ADMIN_BUYERS_COMMAND:
                return new AdminBuyersCommand();
            case ADMIN_EDIT_PRODUCTS_COMMAND:
                return new AdminEditProductsCommand();
            case ADMIN_EDIT_PRODUCTS_VIEW_COMMAND:
                return new AdminEditProductsViewCommand();
            case ADMIN_SEND_MAIL_COMMAND:
                return new AdminSendMailCommand();
            case DISABLE_ACCESS_COMMAND:
                return new DisableAccessCommand();
            case ENABLE_ACCESS_COMMAND:
                return new EnableAccessCommand();
            case ENABLE_PRODUCT_COMMAND:
                return new EnableProductCommand();
            case GENERATE_BUYER_COMMAND:
                return new GenerateBuyerCommand();
            case SHOW_UNAVAILABLE_PRODUCTS_COMMAND:
                return new ShowUnavailableProductsCommand();
            case SHOW_WINNER_COMMAND:
                return new ShowWinnerCommand();
            //manager commands
            case ADD_EMPLOYEE_COMMAND:
                return new AddEmployeeCommand();
            case ADD_PRODUCT_COMMAND:
                return new AddProductCommand();
            case DELETE_EMPLOYEE_COMMAND:
                return new DeleteEmployeeCommand();
            case VIEW_ADD_PERSONAL_COMMAND:
                return new ViewAddPersonalCommand();
            case VIEW_ADD_PRODUCT_COMMAND:
                return new ViewAddProductCommand();
            case VIEW_EARNINGS_COMMAND:
                return new ViewEarningsCommand();
            case VIEW_PERSONAL_COMMAND:
                return new ViewPersonalCommand();
            case DELETE_PRODUCT_COMMAND:
                return new DeleteProductCommand();
            default:
                throw new IllegalArgumentException("No such command");
        }

    }

    /**
     * Returns object of command class which corresponds to command name.
     *
     * @return command corresponding to command name
     * @throws CommandException if command wasn't found
     */
    public static Command getCommand(CommandName commandName) throws CommandException {
        Command command = null;
        try {
            command = getCommandByName(commandName);
            return command;
        } catch (IllegalArgumentException e) {
            throw new CommandException(e);
        }
    }
}
