package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.CountryService;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * Command for viewing add product form
 */
public class ViewAddProductCommand extends ManagerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ProducerService producerService = factory.getService(ServiceType.PRODUCER);
            List<Producer> producers = producerService.findAll();
            request.setAttribute(AttributeConstant.PRODUCERS.value(), producers);

            CategoryService categoryService = factory.getService(ServiceType.CATEGORY);
            Category simpleCategory = categoryService.getSimpleCategory();
            request.setAttribute(AttributeConstant.CATEGORY.value(), simpleCategory);

            CountryService service = factory.getService(ServiceType.COUNTRY);
            List<String> countries = service.readNames();
            request.setAttribute(AttributeConstant.COUNTRIES.value(), countries);
        } catch (ServicePersistentException e) {
        }
        return new Forward(PathConstant.PRODUCTS_CREATE_JSP);
    }
}
