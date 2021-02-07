package music_shop.service;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductRateServiceTest {
    ProductRateService productRateService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        productRateService = serviceFactory.getService(ServiceType.PRODUCT_RATE);
    }

    @Test
    public void countAverageNotNull() throws ServicePersistentException {
        Assert.assertNotNull(productRateService.countAverageRate());
    }
}
