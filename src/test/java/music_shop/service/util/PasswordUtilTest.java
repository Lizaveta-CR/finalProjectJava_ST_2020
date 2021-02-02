package music_shop.service.util;

import by.tsvirko.music_shop.service.exception.PasswordException;
import by.tsvirko.music_shop.service.util.PasswordUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class PasswordUtilTest {
    @DataProvider(name = "passData")
    public Object[] data() throws IOException {
        return new Object[]{
                " ", "1", "pass"
        };
    }

    @Test(dataProvider = "passData")
    public void testPass(String pass) throws PasswordException {
        Assert.assertNotNull(PasswordUtil.hashPassword(pass));
    }

    @Test()
    public void testPassException() throws PasswordException {
        Assert.assertThrows(PasswordException.class, () -> PasswordUtil.hashPassword(null));
    }
}
