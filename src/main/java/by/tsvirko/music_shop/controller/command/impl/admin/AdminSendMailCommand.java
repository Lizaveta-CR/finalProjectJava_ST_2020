package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.mail.MailThreadService;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Command for sending email to buyer and adding bonus. Only admin access
 */
public class AdminSendMailCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(AdminSendMailCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            inputStream = contextClassLoader.getResourceAsStream(ParameterConstant.MAIL_PROP.value());
            if (inputStream != null) {
                properties.load(inputStream);
                MailThreadService threadService = new MailThreadService(request.getParameter(ParameterConstant.TO.value()),
                        request.getParameter(ParameterConstant.SUBJECT.value()),
                        request.getParameter(ParameterConstant.BODY.value()), properties);
                threadService.start();
            }
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        String buyerParameter = request.getParameter(ParameterConstant.BUYER_ID.value());
        String bonusParameter = request.getParameter(ParameterConstant.BONUS.value());
        if (buyerParameter != null && !buyerParameter.isEmpty()
                && bonusParameter != null && !bonusParameter.isEmpty()) {
            try {
                BuyerService service = factory.getService(ServiceType.BUYER);
                Buyer buyer = service.findById(Integer.parseInt(buyerParameter));
                BigDecimal totalBonus = buyer.getBonus().add(new BigDecimal(bonusParameter));
                buyer.setBonus(totalBonus);
                service.update(buyer);
            } catch (ServicePersistentException e) {
                logger.warn("Buyer can not be updated. Bonus update operation failed");
            }
        }
        ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(request);
        Forward forward = new Forward(PathConstant.ADMIN_BUYERS, true);
        forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.winner.email.success"));
        return forward;
    }
}
