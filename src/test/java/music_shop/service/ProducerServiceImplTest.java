package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class ProducerServiceImplTest {
    ProducerService producerService;
    Producer producer;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        producerService = serviceFactory.getService(ProducerService.class);
    }

    @BeforeTest
    public void createProduct() throws ServicePersistentException {
        producer = new Producer();
        Country country = new Country();
        country.setId(1);
        producer.setName("Kypalina");
        producer.setCountry(country);
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<Producer> products = producerService.findAll();
        Assert.assertNotNull(products);
    }

    @Test
    public void delete() throws ServicePersistentException {
        producerService.delete(producer.getId());
        List<Producer> users = producerService.findAll();
        Assert.assertFalse(users.contains(producer));
    }

    @Test
    public void deleteException() {
        Assert.assertThrows(ServicePersistentException.class, () -> producerService.delete(100));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        producerService.save(producer);
        Assert.assertNotNull(producer.getId());
    }

    @Test
    public void saveExceptionTest() throws ServicePersistentException {
        Country country = new Country();
        country.setId(1000);
        producer.setCountry(country);
        Assert.assertThrows(ServicePersistentException.class, () -> producerService.save(producer));
    }

    @DataProvider(name = "names")
    public Object[] createCorrecttData() {

        return new Object[]{
                "Nakahima"
        };
    }

    @Test(dataProvider = "names")
    public void updateTest(String name) throws ServicePersistentException {
        producer.setName(name);
        producerService.save(producer);
        Assert.assertEquals(name, producer.getName());
    }

    @Test()
    public void updateExceptionTest() {
        producer.setName("Takamine");
        Country country = new Country();
        country.setId(1);
        Assert.assertThrows(ServicePersistentException.class, () -> producerService.save(producer));
    }
}
