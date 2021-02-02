package music_shop.service.helper;

import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.helper.TotalPriceHelper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TotalPriceHelperTest {
    private TotalPriceHelper totalPriceUtil = new TotalPriceHelper();


    @DataProvider(name = "priceData")
    public Object[] priceData() {
        Map<Product, Byte> mapF = new HashMap<>();
        Product productF = new Product();
        productF.setPrice(new BigDecimal(100.00));
        mapF.put(productF, (byte) 2);

        Map<Product, Byte> mapS = new HashMap<>();
        Product productS = new Product();
        productS.setPrice(new BigDecimal(200.00));
        mapS.put(productS, (byte) 2);
        return new Object[][]{
                {mapF, new BigDecimal(200.00)},
                {mapS, new BigDecimal(400.00)}
        };
    }

    @DataProvider(name = "orderData")
    public Object[] data() {
        Order orderF = new Order();
        orderF.setPrice(new BigDecimal(1000));
        Order orderS = new Order();
        orderS.setPrice(new BigDecimal(2000));
        Order orderT = new Order();
        orderT.setPrice(new BigDecimal(3000));
        Order order = new Order();
        order.setPrice(new BigDecimal(0));
        return new Object[][]{
                {order, new BigDecimal(1)},
                {orderF, new BigDecimal(2)},
                {orderS, new BigDecimal(3)},
                {orderT, new BigDecimal(4)}
        };
    }

    @Test(dataProvider = "priceData")
    public void countPriceTest(Map<Product, Byte> map, BigDecimal total) {
        BigDecimal actual = totalPriceUtil.countPrice(map);
        BigDecimal expected = total;
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "orderData")
    public void countBonus(Order order, BigDecimal koef) {
        totalPriceUtil.setKoefficient(koef);
        BigDecimal actual = totalPriceUtil.countBonus(order);
        BigDecimal expected = order.getPrice().multiply(koef)
                .divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void countBonusException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> totalPriceUtil.setKoefficient(null));
    }
}
