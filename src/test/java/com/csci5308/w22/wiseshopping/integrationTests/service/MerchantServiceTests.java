package com.csci5308.w22.wiseshopping.integrationTests.service;

import com.csci5308.w22.wiseshopping.models.*;
import com.csci5308.w22.wiseshopping.models.Tags;
import com.csci5308.w22.wiseshopping.repository.*;
import com.csci5308.w22.wiseshopping.service.LocationService;
import com.csci5308.w22.wiseshopping.service.MerchantService;
import com.csci5308.w22.wiseshopping.service.StoreService;
import net.bytebuddy.build.Plugin;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.util.List;

/**
 * @author Elizabeth James
 */
@SpringBootTest
@ActiveProfiles(profiles = "dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MerchantServiceTests {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private StoreService storeService;

    private Merchant merchant;
    private Location location;

    @BeforeEach
    public void setUp(){
        merchant = new Merchant("John Doe", "johndoe@xyz.com", "password123");
        location =  locationService.addLocation("dummy","dummy","dummy","dummy");
    }

    @AfterAll()
    public void cleanUp(){
        merchantService.removeMerchant("johndoe@xyz.com");
        locationService.remove(location);
    }
    @Test
    @Order(1)
    public void testRegisterMerchant(){
        Merchant actualMerchant = merchantService.registerMerchant("John Doe","johndoe@xyz.com","password123");
        merchant.setMerchantId(actualMerchant.getMerchantId());
        Assertions.assertEquals(merchant,actualMerchant);

    }
    @Test
    @Order(2)
    public void testLoginMerchant(){
        Merchant actualMerchant = merchantService.loginMerchant("johndoe@xyz.com","password123");
        Assertions.assertEquals(merchant.getPassword(),actualMerchant.getPassword());
    }


    @Test
    @Order(3)
    public void testRemoveExistingMerchant(){
        merchantService.registerMerchant("John Doe2","johndoe2@xyz.com","password123");
        Assertions.assertTrue(merchantService.removeMerchant("johndoe2@xyz.com"));
    }

    @Test
    @Order(4)
    public void testRemoveNonExistingMerchant(){
        Assertions.assertFalse(merchantService.removeMerchant("johndoe2@xyz.com"));
    }

    @Test
    @Order(5)
    public void testUpdateProductPrice(){
        Product p = productRepository.findByProductId(1);
        Store store = storeRepository.findByStoreId(1);
        if (store == null) {
            store = storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);
        }
        Assertions.assertEquals(4, productInventoryRepository.findByProductAndStore(p, store).getPrice());
        merchantService.updateProductPrice(p, store, 100);

        Assertions.assertEquals(100, productInventoryRepository.findByProductAndStore(p, store).getPrice());
        merchantService.updateProductPrice(p, store, 4);
    }

    @Test
    @Order(6)
    public void testUpdateProductStock(){
        Product p = productRepository.findByProductId(1);
        Store store = storeRepository.findByStoreId(1);
        if (store == null) {
            store = storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);
        }
        Assertions.assertEquals(10, productInventoryRepository.findByProductAndStore(p, store).getStock());
        merchantService.updateProductPrice(p, store, 500);

        Assertions.assertEquals(500, productInventoryRepository.findByProductAndStore(p, store).getPrice());
        merchantService.updateProductPrice(p, store, 10);
    }

    @Test
    @Order(6)
    public void testUpdateProductCategoryName(){
        Assertions.assertEquals("groceries", productCategoryRepository.findByProductCategoryId(1).getCategoryName());
        merchantService.updateProductCategoryName(1, "groceries_updated");

        Assertions.assertEquals("groceries_updated", productCategoryRepository.findByProductCategoryId(1).getCategoryName());
        merchantService.updateProductCategoryName(1, "groceries");
    }

    @Test
    @Order(7)
    public void testUpdateProductCategoryDesc(){
        Assertions.assertEquals("Farmers_Milk", productCategoryRepository.findByProductCategoryId(1).getCategoryDesc());
        merchantService.updateProductCategoryName(1, "Farmers_Milk_updated");

        Assertions.assertEquals("Farmers_Milk_updated", productCategoryRepository.findByProductCategoryId(1).getCategoryName());
        merchantService.updateProductCategoryName(1, "Farmers_Milk");
    }

    @Test
    @Order(8)
    public void testUpdateProductTags(){

        Product p = productRepository.findByProductId(1);
        List<Tags> tags = tagsRepository.findByProductId(1);

        Tags newTag = merchantService.updateProductTags(p, "NewTag");
        List<Tags> tags1 = tagsRepository.findByProductId(1);
        tags1.removeAll(tags);

        Assertions.assertEquals(1, tags1.size());
        Assertions.assertEquals("NewTag", tags1.get(0).getTagName());
        tagsRepository.delete(newTag);
    }


}
