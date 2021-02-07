package music_shop.service;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.service.ProducerItemService;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProducerItemServiceTest {
    ProducerItemService producerItemService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
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
