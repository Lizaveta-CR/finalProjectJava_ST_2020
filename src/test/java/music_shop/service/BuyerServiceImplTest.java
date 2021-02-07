package music_shop.service;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BuyerServiceImplTest {
    BuyerService buyerService;
    Buyer buyer;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        buyerService = serviceFactory.getService(ServiceType.BUYER);
    }

    @BeforeTest
    public void createBuyer() throws ServicePersistentException {
        buyer = new Buyer();
        buyer.setBalance(new BigDecimal(10000));
        buyer.setId(8);
        Address address = new Address();
        address.setId(buyer.getId());
        buyer.setAddress(address);
        buyer.setTelephone(37511684711l);
        buyer.setEnabled(true);
        buyer.setEmail("test@gmail.com");
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<Buyer> buyers = buyerService.findAll();
        Assert.assertNotNull(buyers);
    }

    @Test
    public void delete() throws ServicePersistentException {
        buyerService.delete(buyer.getId());
        List<Buyer> users = buyerService.findAll();
        Assert.assertFalse(users.contains(buyer));
    }

    @Test
    public void deleteException() {
        Assert.assertThrows(ServicePersistentException.class, () -> buyerService.delete(100));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        buyerService.save(buyer);
        Assert.assertNotNull(buyer.getId());
    }

    @Test
    public void saveExceptionTest() throws ServicePersistentException {
        buyer.setEmail(null);
        Assert.assertThrows(ServicePersistentException.class, () -> buyerService.save(buyer));
    }

    @DataProvider(name = "emails")
    public Object[] createCorrecttData() {

        return new Object[]{
                "testUpdate@gmail.com"
        };
    }

    @Test(dataProvider = "emails")
    public void updateTest(String email) throws ServicePersistentException {
        buyer.setEmail(email);
        buyerService.update(buyer);
        Assert.assertEquals(email, buyer.getEmail());
    }

    @Test
    public void findByIdExceptionTest() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> buyerService.findById(1000));
    }

    @Test
    public void findByIdTest() throws ServicePersistentException {
        Buyer found = buyerService.findById(buyer.getId());
        Assert.assertEquals(buyer.getId(), found.getId());
    }

    @DataProvider(name = "offsetsRecords")
    public Object[] offsets() {
        return new Object[][]{
                {1, 1},
                {0, 1},
                {2, 3}
        };
    }

    @Test(dataProvider = "offsetsRecords")
    public void findAllOffset(Integer offset, Integer noOfRecords) throws ServicePersistentException {
        Map<Integer, List<Buyer>> map = buyerService.find(offset, noOfRecords);
        Assert.assertNotNull(map);
    }

    @Test
    public void findException() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> buyerService.find(100));
    }

    @Test
    public void findRandomByOrdersAmountEquals() throws ServicePersistentException {
        Buyer foundBuyer = buyerService.find(this.buyer.getOrders().size());
        Assert.assertEquals(buyer.getOrders().size(), foundBuyer.getOrders().size());
    }

    @Test
    public void findRandomByOrdersAmountNotNull() throws ServicePersistentException {
        Assert.assertNotNull(buyerService.find(this.buyer.getOrders().size()));
    }

}
