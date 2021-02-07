package music_shop.service;

import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class ProducerServiceImplTest {
    ProducerService producerService;
    Producer producer;

    @BeforeClass
    public void setUpBeforeClass() throws ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        producerService = serviceFactory.getService(ServiceType.PRODUCER);
    }

    @BeforeTest
    public void createProduct() {
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
    public void saveExceptionTest() {
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

    @Test
    public void findByIdExceptionTest() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> producerService.findById(1000));
    }

    @Test
    public void findByIdTest() throws ServicePersistentException {
        Producer foundUser = producerService.findById(producer.getId());
        Assert.assertEquals(producer.getId(), foundUser.getId());
    }
}
