package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
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
import java.util.Map;

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
        buyer.setBalance(new BigDecimal(100000));
        buyer.setId(2);
        Address address = new Address();
        address.setId(2);
        buyer.setAddress(address);
        buyer.setTelephone(375445684711l);
        buyer.setEnabled(true);
        buyer.setId(4);
        buyer.setEmail("elizT@gmail.com");
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

    @DataProvider(name = "offsetsRecords")
    public Object[] offsets() {
        return new Object[][]{
                {1, 1},
                {0, 1},
                {2, 4}
        };
    }

    @Test(dataProvider = "offsetsRecords")
    public void findAllOffset(Integer offset, Integer noOfRecords) throws ServicePersistentException {
        Map<Integer, List<Order>> map = orderService.find(offset, noOfRecords,order.getBuyer().getId());
        Assert.assertNotNull(map);
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

    @DataProvider(name = "incorrectBalance")
    public Object[] createIncorrectBalance() {
        return new Object[]{
                new BigDecimal(0),
                new BigDecimal(-100),
                new BigDecimal(2)
        };
    }

    @Test
    public void saveHaveMonetTest() throws ServicePersistentException {
        orderService.save(order);
        Assert.assertNotNull(order.getId());
        boolean isAvailable = (order.getBuyer().getBalance().subtract(order.getPrice())).compareTo(BigDecimal.ZERO) > 0;
        Assert.assertTrue(isAvailable);
    }

    @Test(dataProvider = "incorrectBalance")
    public void saveNoMonetTest(BigDecimal balance) {
        Buyer buyer = order.getBuyer();
        buyer.setBalance(balance);
        order.setId(null);
        Assert.assertThrows(ServicePersistentException.class, () -> orderService.save(order));
    }

    @Test
    public void updateTest() throws ServicePersistentException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse("2021-07-05");
        order.setDate(date);
        order.getBuyer().setBalance(new BigDecimal(10000));
        orderService.save(order);
        Assert.assertEquals(order.getDate(), date);
    }
}
