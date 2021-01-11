package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRateServiceTest {
    ProductRateService productRateService;
    ProductService productService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        productRateService = serviceFactory.getService(ProductRateService.class);
        productService = serviceFactory.getService(ProductService.class);
    }

    @Test
    public void countAverageNotNull() throws ServicePersistentException {
        Assert.assertNotNull(productRateService.countAverageRate());
    }
}
