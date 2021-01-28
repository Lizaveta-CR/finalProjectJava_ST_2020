package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImplTest {
    ProductService productService;
    Product product;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        productService = serviceFactory.getService(ProductService.class);
    }

    @BeforeTest
    public void createProduct() throws ServicePersistentException {
        product = new Product();
        Category category = new Category();
        category.setId(3);
        Producer producer = new Producer();
        producer.setId(1);
        product.setProducer(producer);
        product.setCategory(category);
        product.setModel("H5-Gibson 3000");
        product.setAvailable(true);
        product.setPrice(new BigDecimal(100.00));
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<Product> products = productService.findAll();
        Assert.assertNotNull(products);
    }

    @Test
    public void delete() throws ServicePersistentException {
        productService.delete(product.getId());
        List<Product> users = productService.findAll();
        Assert.assertFalse(users.contains(product));
    }

    @Test
    public void deleteException() {
        Assert.assertThrows(ServicePersistentException.class, () -> productService.delete(100));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        productService.save(product);
        Assert.assertNotNull(product.getId());
    }

    @Test
    public void saveExceptionTest() throws ServicePersistentException {
        Category category = new Category();
        category.setId(1000);
        product.setCategory(category);
        Assert.assertThrows(ServicePersistentException.class, () -> productService.save(product));
    }

    @DataProvider(name = "models")
    public Object[] createCorrecttData() {

        return new Object[]{
                "W3-Abbra 3000"
        };
    }

    @Test(dataProvider = "models")
    public void updateTest(String name) throws ServicePersistentException {
        product.setModel(name);
        productService.save(product);
        Assert.assertEquals(name, product.getModel());
    }

    @Test()
    public void updateExceptionTest() {
        product.setModel(null);
        Assert.assertThrows(ServicePersistentException.class, () -> productService.save(product));
    }

    @Test
    public void findByIdExceptionTest() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> productService.findById(1000));
    }

    @Test
    public void findByIdTest() throws ServicePersistentException {
        Product foundProduct = productService.findById(product.getId());
        Assert.assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    public void findNotAvailableTest() throws ServicePersistentException {
        List<Product> notAvailable = productService.findNotAvailable();
        notAvailable.forEach(product -> Assert.assertTrue(product.getAvailable() == false));
    }

    @DataProvider(name = "marks")
    public Object[] createCorrectMarks() {

        return new Object[]{
                8, 9
        };
    }

    @Test(dataProvider = "marks")
    public void readProductsByMark(Integer mark) throws ServicePersistentException {
        List<Product> products = productService.readProductsByMark(mark);
        Assert.assertNotNull(products);
    }

    @Test
    public void readProductsByMarkException() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> productService.readProductsByMark(100));
    }
}
