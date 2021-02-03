package music_shop.tag;

import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.tag.CustomFunction;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CustomFunctionTest {
    private CustomFunction function;

    @DataProvider(name = "decimal")
    public Object[] createData() {
        return new Object[]{
                new BigDecimal(10),
                new BigDecimal(200),
                new BigDecimal(-1),
                null,
        };
    }

    @DataProvider(name = "equalsData")
    public Object[][] createDataEquals() {
        return new Object[][]{
                {"asd", "asd"},
                {"12", "12"},
        };
    }

    @Test
    public void isBigDecimalZeroTest() {
        Assert.assertTrue(function.isBigDecimalZero(new BigDecimal(0)));
    }

    @Test(dataProvider = "decimal")
    public void isBigDecimalZeroNegativeTest(BigDecimal decimal) {
        Assert.assertFalse(function.isBigDecimalZero(decimal));
    }

    @Test(dataProvider = "equalsData")
    public void equalsString(String str1, String str2) {
        Assert.assertTrue(function.equals(str1, str2));
    }

    @Test
    public void equalsStringNull() {
        Assert.assertFalse(function.equals(null, "str2"));
        Assert.assertFalse(function.equals(null, null));
    }

    @Test
    public void isBuyerTest() {
        User user = new User();
        user.setRole(Role.BUYER);
        Assert.assertTrue(function.isBuyer(user));
    }

    @Test
    public void isAdminTest() {
        User user = new User();
        user.setRole(Role.ADMINISTRATOR);
        Assert.assertTrue(function.isAdmin(user));
    }

    @Test
    public void isManagerTest() {
        User user = new User();
        user.setRole(Role.MANAGER);
        Assert.assertTrue(function.isManager(user));
    }

    @Test
    public void isBuyerNullTest() {
        Assert.assertFalse(function.isBuyer(null));
    }

    @Test
    public void isManagerNullTest() {
        Assert.assertFalse(function.isManager(null));
    }

    @Test
    public void isAdminNullTest() {
        Assert.assertFalse(function.isAdmin(null));
    }
}
