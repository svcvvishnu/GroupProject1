package com.csci5308.w22.wiseshopping.service;

import com.csci5308.w22.wiseshopping.models.*;
import com.csci5308.w22.wiseshopping.repository.MerchantRepository;
import com.csci5308.w22.wiseshopping.repository.ProductCategoryRepository;
import com.csci5308.w22.wiseshopping.repository.ProductInventoryRepository;
import com.csci5308.w22.wiseshopping.repository.TagsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * @author Elizabeth James
 */
@ExtendWith(MockitoExtension.class)
public class MerchantServiceTests {
    @Mock
    private MerchantRepository mockedMerchantRepository;

    @Mock
    private ProductCategoryRepository mockedCategoryRepository;

    @Mock
    private ProductInventoryRepository mockedInventoryRepository;

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private MerchantService merchantService;

    private Merchant merchant;

    @BeforeEach
    public void setUp(){
         merchant = new Merchant("John Doe", "johndoe@xyz.com", "password123");
    }

    @Test
    public void testRegisterMerchant() {
        when(mockedMerchantRepository.save(any(Merchant.class))).thenReturn(merchant);
        Merchant actualMerchant = merchantService.registerMerchant("John Doe", "johndoe@xyz.com", "password123");
        Assertions.assertEquals(merchant, actualMerchant);
    }


    @Test
    public void testInputParametersForRegisterMerchant(){
        IllegalArgumentException exceptionForName1 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant(null,"dummy","dummy"));
        Assertions.assertEquals("name cannot be null or empty or blank",exceptionForName1.getMessage());
        IllegalArgumentException exceptionForName2 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("","dummy","dummy"));
        Assertions.assertEquals("name cannot be null or empty or blank",exceptionForName2.getMessage());
        IllegalArgumentException exceptionForName3 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant(" ","dummy","dummy"));
        Assertions.assertEquals("name cannot be null or empty or blank",exceptionForName3.getMessage());

        IllegalArgumentException exceptionForEmail1 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy",null,"dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail1.getMessage());
        IllegalArgumentException exceptionForEmail2 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","","dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail2.getMessage());
        IllegalArgumentException exceptionForEmail3 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy"," ","dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail3.getMessage());

        IllegalArgumentException exceptionForPassword1 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","dummy",null));
        Assertions.assertEquals("password cannot be null or empty or blank",exceptionForPassword1.getMessage());
        IllegalArgumentException exceptionForPassword2 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","dummy",""));
        Assertions.assertEquals("password cannot be null or empty or blank",exceptionForPassword2.getMessage());
        IllegalArgumentException exceptionForPassword3 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","dummy"," "));
        Assertions.assertEquals("password cannot be null or empty or blank",exceptionForPassword3.getMessage());

        IllegalArgumentException exceptionForEmail4 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","dummy","dummy"));
        Assertions.assertEquals("given email is not valid",exceptionForEmail4.getMessage());

    }

    @Test
    public void testRemoveMerchantForExistingRow(){
        when(mockedMerchantRepository.deleteByEmail(any(String.class))).thenReturn(1);
        Assertions.assertTrue(merchantService.removeMerchant("n@n.com"));
    }

    @Test
    public void testRemoveMerchantForNonExistingRow(){
        when(mockedMerchantRepository.deleteByEmail(any(String.class))).thenReturn(0);
        Assertions.assertFalse(merchantService.removeMerchant("n@n.com"));
    }

    @Test
    public void testInputParametersForRemoveMerchant(){
        IllegalArgumentException exceptionForEmail1 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy",null,"dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail1.getMessage());
        IllegalArgumentException exceptionForEmail2 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy","","dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail2.getMessage());
        IllegalArgumentException exceptionForEmail3 = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.registerMerchant("dummy"," ","dummy"));
        Assertions.assertEquals("email cannot be null or empty or blank",exceptionForEmail3.getMessage());

    }

    @Test
    public void testIllegalArgumentsRemoveNonExistingMerchant(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.removeMerchant(null));
        Assertions.assertEquals("email cannot be null or empty or blank",exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.removeMerchant(""));
        Assertions.assertEquals("email cannot be null or empty or blank",exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.removeMerchant(" "));
        Assertions.assertEquals("email cannot be null or empty or blank",exception.getMessage());
    }

    @Test
    public void testLoginMerchant()  {
        when(mockedMerchantRepository.findMerchantByEmailAndPassword(any(String.class),any(String.class))).thenReturn(merchant);
        Merchant actualMerchant = merchantService.loginMerchant("johndoe@xyz.com", "password123");
        Assertions.assertEquals(merchant, actualMerchant);
    }

    @Test
    public void testInputParametersForLoginMerchant(){

        IllegalArgumentException emailNullException=Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.loginMerchant(null,"test_password"));
        Assertions.assertEquals("email cannot be null",emailNullException.getMessage());
        IllegalArgumentException emailEmptyException=Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.loginMerchant("","test_password"));
        Assertions.assertEquals("email cannot be empty",emailEmptyException.getMessage());
        IllegalArgumentException emailMissingDomainNameException=Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.loginMerchant("test_email","test_password"));
        Assertions.assertEquals("given email id is not valid",emailMissingDomainNameException.getMessage());

        IllegalArgumentException passwordEmptyException=Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.loginMerchant("test_email@xyz.com",""));
        Assertions.assertEquals("password cannot be empty",passwordEmptyException.getMessage());
        IllegalArgumentException passwordNullException=Assertions.assertThrows(IllegalArgumentException.class, () -> merchantService.loginMerchant("test_email@xyz.com",null));
        Assertions.assertEquals("password cannot be null",passwordNullException.getMessage());
    }
    @Test
    public void testUpdateProductPrice(){
        Product product = new Product();
        Store store = new Store();
        ProductInventory inventory = new ProductInventory( store, product, 123, 456);
        when(mockedInventoryRepository.findByProductAndStore(product,store)).thenReturn(inventory);

        ProductInventory updated = merchantService.updateProductPrice(product, store, 1000);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(1000, updated.getPrice());
    }

    @Test
    public void testUpdateProductStock(){
        Product product = new Product();
        Store store = new Store();
        ProductInventory inventory = new ProductInventory( store, product, 123, 456);
        when(mockedInventoryRepository.findByProductAndStore(product,store)).thenReturn(inventory);

        ProductInventory updated = merchantService.updateProductStock(product, store, 8888);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(8888, updated.getStock());
    }

    @Test
    public void testUpdateProductPriceInvalidProductStore(){
        Product product = new Product();
        Store store = new Store();
        when(mockedInventoryRepository.findByProductAndStore(product,store)).thenReturn(null);

        IllegalArgumentException ex = Assertions.assertThrows( IllegalArgumentException.class,
                () -> merchantService.updateProductPrice(product, store, 1000), "Exception not thrown");
        Assertions.assertTrue(ex.getMessage().contains("Could not find inventory with given Product in store"));
    }


    @Test
    public void testUpdateProductStockInvalidProductStore(){
        Product product = new Product();
        Store store = new Store();
        when(mockedInventoryRepository.findByProductAndStore(product,store)).thenReturn(null);

        IllegalArgumentException ex = Assertions.assertThrows( IllegalArgumentException.class,
                () -> merchantService.updateProductStock(product, store, 1000), "Exception not thrown");
        Assertions.assertTrue(ex.getMessage().contains("Could not find inventory with given Product in store"));
    }

    @Test
    public void testUpdateProductCategoryName(){
        Product product = new Product();
        ProductCategory category = new ProductCategory( product, "Category A", "Category A Desc");
        when(mockedCategoryRepository.findByProductCategoryId(any(Integer.class))).thenReturn(category);

        ProductCategory updated = merchantService.updateProductCategoryName(1, "Category Name Updated");

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Category Name Updated", updated.getCategoryName());
    }

    @Test
    public void testUpdateProductCategoryDesc(){
        Product product = new Product();
        ProductCategory category = new ProductCategory( product, "Category A", "Category A Desc");
        when(mockedCategoryRepository.findByProductCategoryId(any(Integer.class))).thenReturn(category);

        ProductCategory updated = merchantService.updateProductCategoryDescription(1, "Category Desc Updated");

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Category Desc Updated", updated.getCategoryDesc());
    }

    @Test
    public void testUpdateProductCategoryNameInvalidProduct(){
        when(mockedCategoryRepository.findByProductCategoryId(any(Integer.class))).thenReturn(null);

        IllegalArgumentException ex = Assertions.assertThrows( IllegalArgumentException.class,
                () -> merchantService.updateProductCategoryName(1, "Category Name Updated"), "Exception not thrown");
        Assertions.assertTrue(ex.getMessage().contains("Could not find category with given Id:"));
    }

    @Test
    public void testUpdateProductCategoryDescInvalidProduct(){
        when(mockedCategoryRepository.findByProductCategoryId(any(Integer.class))).thenReturn(null);

        IllegalArgumentException ex = Assertions.assertThrows( IllegalArgumentException.class,
                () -> merchantService.updateProductCategoryDescription(1, "Category Desc Updated"), "Exception not thrown");
        Assertions.assertTrue(ex.getMessage().contains("Could not find category with given Id:"));
    }

    @Test
    public void testUpdateProductTags(){

        Tags tag = new Tags(new Product("ProductName", "ProductDesc"), "Tag1");

        when(tagsRepository.save(any())).thenReturn(tag);
        Tags newTag = merchantService.updateProductTags(tag.getProduct(), tag.getTagName());

        Assertions.assertEquals(tag.getProduct(), newTag.getProduct());
        Assertions.assertEquals(tag.getTagName(), newTag.getTagName());
    }




}
