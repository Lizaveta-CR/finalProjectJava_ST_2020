package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.mail.MailThreadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdminSendMailCommand extends AdminCommand {

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
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return new Forward(PathConstnant.ADMIN_BUYERS, true);
    }
}
