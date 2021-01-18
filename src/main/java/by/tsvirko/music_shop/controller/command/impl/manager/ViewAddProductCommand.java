package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.CountryService;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAddProductCommand extends ManagerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ProducerService producerService = factory.getService(ProducerService.class);
            List<Producer> producers = producerService.findAll();
            request.setAttribute(AttributeConstant.PRODUCERS.value(), producers);

            CategoryService categoryService = factory.getService(CategoryService.class);
            Category simpleCategory = categoryService.getSimpleCategory();
            request.setAttribute(AttributeConstant.CATEGORY.value(), simpleCategory);

            CountryService service = factory.getService(CountryService.class);
            List<String> countries = service.readNames();
            request.setAttribute(AttributeConstant.COUNTRIES.value(), countries);
        } catch (ServicePersistentException e) {
        }
        return null;
    }
}
