package music_shop.service;

import by.tsvirko.music_shop.dal.transaction.impl.TransactionFactoryImpl;
import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemServiceImpTest {
    OrderItemService orderItemService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        orderItemService = serviceFactory.getService(ServiceType.ORDER_ITEM);
    }

    @DataProvider(name = "orderItemCorrect")
    public Object[] createCorrectData() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(7);
        orderItem.setPrice(new BigDecimal(1000));
        Product product = new Product();
        product.setId(1);
        orderItem.setProduct(product);
        orderItem.setAmount((byte) 1);

        return new Object[]{
                orderItem
        };
    }

    @DataProvider(name = "orderItemInCorrect")
    public Object[] createIncorrectData() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setPrice(null);
        Product product = new Product();
        product.setId(1);
        orderItem.setProduct(product);
        orderItem.setAmount((byte) 1);

        return new Object[]{
                orderItem
        };
    }


    @Test(dataProvider = "orderItemInCorrect")
    public void saveExceptionTest(OrderItem orderItem) {
        Assert.assertThrows(ServicePersistentException.class, () -> orderItemService.save(orderItem));
    }

    @Test(dataProvider = "orderItemCorrect")
    public void saveTest(OrderItem orderItem) throws ServicePersistentException {
        orderItemService.save(orderItem);
        Assert.assertNotNull(orderItem.getId());
    }

    @Test(dataProvider = "orderItemCorrect")
    public void deleteTest(OrderItem orderItem) throws ServicePersistentException {
        Integer orderItemId = orderItem.getId();
        orderItemService.delete(orderItemId);
        Assert.assertThrows(ServicePersistentException.class, () -> orderItemService.readProductsByOrderId(orderItemId));
    }

    @Test(dataProvider = "orderItemCorrect")
    public void readProductsById(OrderItem orderItem) throws ServicePersistentException {
        List<Product> products = orderItemService.readProductsByOrderId(orderItem.getId());
        Assert.assertNotNull(products);
    }

    @Test
    public void deleteExceptionTest() {
        Assert.assertThrows(ServicePersistentException.class, () -> orderItemService.delete(100));
    }

}
