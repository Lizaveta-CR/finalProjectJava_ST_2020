package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;
import java.util.Set;

public class ShowProducerCommand extends Command {
    private static final Logger logger = LogManager.getLogger(ShowProducerCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(request);
        String parameter = request.getParameter(ParameterConstant.PRODUCER_ID.value());
        if (parameter != null) {
            try {
                ProducerService producerService = factory.getService(ServiceType.PRODUCER);
                Producer producer = producerService.findById(Integer.parseInt(parameter));
                request.setAttribute(AttributeConstant.PRODUCER.value(), producer);
            } catch (ServicePersistentException e) {
                logger.info("Producer with id=" + parameter + " can not be found:" + e.getMessage());
                Forward forward = new Forward(PathConstant.PRODUCT_PRODUCER, true);
                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.noProducer"));
                return forward;
            }
        }
        return null;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
