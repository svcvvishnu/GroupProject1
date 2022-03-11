package com.csci5308.w22.wiseshopping.integrationTests.service;

import com.csci5308.w22.wiseshopping.models.Location;
import com.csci5308.w22.wiseshopping.models.Merchant;
import com.csci5308.w22.wiseshopping.models.Store;
import com.csci5308.w22.wiseshopping.service.LocationService;
import com.csci5308.w22.wiseshopping.service.MerchantService;
import com.csci5308.w22.wiseshopping.service.StoreService;
import com.csci5308.w22.wiseshopping.utils.Constants;
import com.csci5308.w22.wiseshopping.utils.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elizabeth James
 */
//TODO: change profile to test later
@SpringBootTest
@ActiveProfiles(profiles = "dev")
public class StoreServiceTests {
    @Autowired
    private StoreService storeService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private LocationService locationService;

    private Merchant merchant;
    private Location location;

    @BeforeEach
    public void setUp(){
        merchant = merchantService.registerMerchant("dummy", "dummy@dummy.com", "dummy");
        location =  locationService.addLocation("dummy","dummy","dummy","dummy");

    }
    @Test
    public void testAddStore() {

        Store expectedStore  = new Store("Timbuktu", Util.parseTime("11"), Util.parseTime("12"), "private", "John Doe", location,merchant);

        Store actualStore = storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);
        expectedStore.setStoreId(actualStore.getStoreId());
        Assertions.assertEquals(expectedStore, actualStore);
        storeService.remove(expectedStore);

    }

    @Test
    public void testUpdateStore() {

        Store expectedStore  = new Store("Timbuktu", Util.parseTime("11"), Util.parseTime("12"), "private", "John Doe", location,merchant);

        Store actualStore = storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.KEY_TYPE_OF_BUSINESS, "none");
        map.put(Constants.KEY_CONTACT, "1");
        map.put(Constants.KEY_NAME, "walmart");
        map.put(Constants.KEY_START_TIME, "11");
        map.put(Constants.KEY_END_TIME,"12");
        Store updatedStore = storeService.updateStore(actualStore, map);
        Assertions.assertNotNull(updatedStore);
        storeService.remove(updatedStore);

    }


    @Test
    public void testRemoveStores(){
        storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);

        Merchant merchant2 = merchantService.getMerchantByEmail("dummy@dummy.com");
        List<Store> storeList = storeService.getAllStoresBelongingToAMerchant(merchant2);
        Assertions.assertTrue(storeService.remove(storeList.stream().findFirst().get().getStoreId()));
    }

    @Test
    public void testGetStoresByLocationAndMerchant(){
        storeService.addStore("Timbuktu", "private", "11", "12", "John Doe", merchant,location);
        List<Store> storeList = storeService.getStoresByLocationAndMerchant(location, merchant);
        Assertions.assertEquals(1, storeList.size());
        Store s = storeList.get(0);
        Assertions.assertEquals("Timbuktu", s.getStoreName());
        Assertions.assertEquals("private", s.getType());
        Assertions.assertEquals("John Doe", s.getContact());
        Assertions.assertEquals(merchant, s.getMerchant());
        Assertions.assertEquals(location, s.getLocation());
    }

    @AfterEach
    public void cleanUp(){
        locationService.remove(location);
        merchantService.removeMerchant("dummy@dummy.com");

    }

}
