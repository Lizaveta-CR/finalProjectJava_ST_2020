package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderServiceImplTest {
    OrderService orderService;
    Order order;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        orderService = serviceFactory.getService(OrderService.class);
    }

    @BeforeTest
    public void createOrder() throws ParseException {
        order = new Order();
        Buyer buyer = new Buyer();
        buyer.setId(4);
        order.setBuyer(buyer);
        order.setPrice(new BigDecimal(1000));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        order.setDate(dateFormat.parse("2020-08-05"));
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<Order> orders = orderService.findAll();
        Assert.assertNotNull(orders);
    }

    @Test
    public void delete() throws ServicePersistentException {
        orderService.delete(order.getId());
        List<Order> users = orderService.findAll();
        Assert.assertFalse(users.contains(order));
    }

    @Test
    public void deleteException() {
        Assert.assertThrows(ServicePersistentException.class, () -> orderService.delete(100));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        orderService.save(order);
        Assert.assertNotNull(order.getId());
    }

    @Test
    public void saveExceptionTest() throws ServicePersistentException {
        Buyer buyer = new Buyer();
        buyer.setId(1000);
        order.setBuyer(buyer);
        Assert.assertThrows(ServicePersistentException.class, () -> orderService.save(order));
    }

    @DataProvider(name = "prices")
    public Object[] createCorrecttData() {

        return new Object[]{
                new BigDecimal(2000)
        };
    }

    @Test(dataProvider = "prices")
    public void updateTest(BigDecimal price) throws ServicePersistentException {
        order.setPrice(price);
        orderService.save(order);
        Assert.assertEquals(price, order.getPrice());
    }

    @Test
    public void updateExceptionTest() {
        order.setPrice(null);
        Assert.assertThrows(ServicePersistentException.class, () -> orderService.save(order));
    }
}
