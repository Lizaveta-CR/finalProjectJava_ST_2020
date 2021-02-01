package music_shop.service.mail;

import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.service.mail.SessionCreator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SessionCreatorTest {
    @DataProvider(name = "sessionData")
    public Object[] data() throws IOException {
        Properties properties = new Properties();
        Thread currentThread = Thread.currentThread();
        ClassLoader contextClassLoader = currentThread.getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream(ParameterConstant.MAIL_PROP.value());
        properties.load(inputStream);
        return new Object[]{
                properties
        };
    }

    @Test(dataProvider = "sessionData")
    public void createSessionTest(Properties properties) {
        Assert.assertNotNull(new SessionCreator(properties).createSession());
    }
}
