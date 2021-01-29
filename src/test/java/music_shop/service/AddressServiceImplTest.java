package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AddressServiceImplTest {
    AddressService addressService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        addressService = serviceFactory.getService(ServiceType.ADDRESS);
    }

    @DataProvider(name = "addressIncorrect")
    public Object[] createIncorrectData() {

        Address address = new Address();
        Country country = new Country();
        country.setName("Japan");
        address.setCountry(country);
        address.setCity("Tokio");
        address.setZipCode(1234);
        address.setApartmentNumber(11);
        address.setHouseNumber(10);
        return new Object[]{
                address
        };
    }

    @DataProvider(name = "addressCorrect")
    public Object[] createCorrectData() {

        Address address = new Address();
        Country country = new Country();
        address.setId(4);
        country.setName("Belarus");
        country.setId(1);
        address.setCountry(country);
        address.setCity("Minsk");
        address.setStreet("Nezavisimosty");
        address.setZipCode(220991);
        address.setApartmentNumber(11);
        address.setHouseNumber(10);
        return new Object[]{
                address
        };
    }

    @Test
    public void findAll() throws ServicePersistentException {
        Assert.assertNotNull(addressService.findAll());
    }

    @Test
    public void deleteExceptionTest() {
        Assert.assertThrows(ServicePersistentException.class, () -> addressService.delete(100));
    }

    @Test(dataProvider = "addressCorrect")
    public void delete(Address address) throws ServicePersistentException {
        addressService.delete(address.getId());
        List<Address> users = addressService.findAll();
        Assert.assertFalse(users.contains(address));
    }

    @Test(dataProvider = "addressIncorrect")
    public void saveExceptionTest(Address address) {
        Assert.assertThrows(ServicePersistentException.class, () -> addressService.save(address));
    }

    @Test(dataProvider = "addressCorrect")
    public void saveTest(Address address) throws ServicePersistentException {
        addressService.save(address);
        Assert.assertNotNull(address.getId());
    }


    @Test(dataProvider = "addressCorrect")
    public void updateTest(Address address) throws ServicePersistentException {
        address.setStreet("Melnikayte");
        addressService.update(address);
        Assert.assertEquals("Melnikayte", address.getStreet());
    }

    @Test(dataProvider = "addressCorrect")
    public void updateExceptionTest(Address address) {
        address.setStreet(null);
        Assert.assertThrows(ServicePersistentException.class, () -> addressService.update(address));
    }

    @Test
    public void findByIdExceptionTest() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> addressService.findById(1000));
    }

    @Test(dataProvider = "addressCorrect")
    public void findByIdTest(Address address) throws ServicePersistentException {
        Address foundAddress = addressService.findById(address.getId());
        Assert.assertEquals(address.getId(), foundAddress.getId());
    }
}
