package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.ProducerItemService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ProducerItemServiceTest {
    ProducerItemService producerItemService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        producerItemService = serviceFactory.getService(ServiceType.PRODUCER_ITEM);
    }

    @Test
    public void readProducerByProductTest() throws ServicePersistentException {
        Assert.assertNotNull(producerItemService.readProducerByProduct(1));
    }

    @Test
    public void readProducerByProductExceptionTest() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> producerItemService.readProducerByProduct(1000));
    }
}
