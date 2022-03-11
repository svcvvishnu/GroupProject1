package com.csci5308.w22.wiseshopping.service;

import com.csci5308.w22.wiseshopping.models.Location;
import com.csci5308.w22.wiseshopping.models.Merchant;
import com.csci5308.w22.wiseshopping.models.Store;
import com.csci5308.w22.wiseshopping.repository.StoreRepository;
import com.csci5308.w22.wiseshopping.utils.Constants;
import com.csci5308.w22.wiseshopping.utils.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Elizabeth James
 */
@ExtendWith(MockitoExtension.class)
public class StoreServiceTests {
    @Mock
    StoreRepository mockedStoreRepository;

    @InjectMocks
    StoreService storeService;

    private Store store;

    @BeforeEach
    public void setUp(){
        store = new Store("Timbuktu", Util.parseTime("11"),Util.parseTime("12"),"private","John Doe",new Location(), new Merchant());
    }

    @Test
    public void testAddLocation(){
        when(mockedStoreRepository.save(any(Store.class))).thenReturn(store);
        Store actualStore = storeService.addStore("Timbuktu","private","11","12","John Doe", new Merchant(), new Location());
        Assertions.assertEquals(store,actualStore);

    }

    @Test
    public void testAddStore(){
        when(mockedStoreRepository.save(any(Store.class))).thenReturn(store);
        Store actualStore = storeService.addStore("Timbuktu","private","11","12","John Doe", new Merchant(), new Location());
        Assertions.assertEquals(store,actualStore);

    }

    @Test
    public void testInputParametersForAddLocation(){
        IllegalArgumentException exceptionForName1 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore(null,"private","11","12","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("storeName cannot be null or empty or blank",exceptionForName1.getMessage());
        IllegalArgumentException exceptionForName2 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("","private","11","12","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("storeName cannot be null or empty or blank",exceptionForName2.getMessage());
        IllegalArgumentException exceptionForName3 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore(" ","private","11","12","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("storeName cannot be null or empty or blank",exceptionForName3.getMessage());

        IllegalArgumentException exceptionForBusinessType1 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu",null,"12","private","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("businessType cannot be null or empty or blank",exceptionForBusinessType1.getMessage());
        IllegalArgumentException exceptionForBusinessType2 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","","11","12","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("businessType cannot be null or empty or blank",exceptionForBusinessType2.getMessage());
        IllegalArgumentException exceptionForBusinessType3 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu"," ","11","12","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("businessType cannot be null or empty or blank",exceptionForBusinessType3.getMessage());

        IllegalArgumentException exceptionForStartTime1 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11",null,"private","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("startTime cannot be null or empty or blank",exceptionForStartTime1.getMessage());
        IllegalArgumentException exceptionForStartTime2 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11","","private","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("startTime cannot be null or empty or blank",exceptionForStartTime2.getMessage());
        IllegalArgumentException exceptionForStartTime3 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11"," ","private","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("startTime cannot be null or empty or blank",exceptionForStartTime3.getMessage());

        IllegalArgumentException exceptionForEndTime1 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11","12",null,"John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("endTime cannot be null or empty or blank",exceptionForEndTime1.getMessage());
        IllegalArgumentException exceptionForEndTime2 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11","12","","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("endTime cannot be null or empty or blank",exceptionForEndTime2.getMessage());
        IllegalArgumentException exceptionForEndTime3 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","11","12"," ","John Doe", new Merchant(), new Location()));
        Assertions.assertEquals("endTime cannot be null or empty or blank",exceptionForEndTime3.getMessage());


        IllegalArgumentException exceptionForContact1 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","private","11","12",null, new Merchant(), new Location()));
        Assertions.assertEquals("contactNumber cannot be null or empty or blank",exceptionForContact1.getMessage());
        IllegalArgumentException exceptionForContact2 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","private","11","12","", new Merchant(), new Location()));
        Assertions.assertEquals("contactNumber cannot be null or empty or blank",exceptionForContact2.getMessage());
        IllegalArgumentException exceptionForContact3 = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","private","11","12"," ", new Merchant(), new Location()));
        Assertions.assertEquals("contactNumber cannot be null or empty or blank",exceptionForContact3.getMessage());

        IllegalArgumentException exceptionForMerchant = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","private","11","12","John Doe",null, new Location()));
        Assertions.assertEquals("merchant cannot be null",exceptionForMerchant.getMessage());
        IllegalArgumentException exceptionForLocation = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.addStore("Timbuktu","private","11","12","John Doe", new Merchant(), null));
        Assertions.assertEquals("location cannot be null",exceptionForLocation.getMessage());
    }

    @Test
    public void testRemoveStore(){
        Assertions.assertTrue(storeService.remove(store));

    }

    @Test
    public void testInvalidArgumentsRemoveStore(){
        IllegalArgumentException exceptionForMerchant = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.remove(null));
        Assertions.assertEquals("store cannot be null",exceptionForMerchant.getMessage());
    }

    @Test
    public void testUpdateStore(){
        when(mockedStoreRepository.save(any(Store.class))).thenReturn(store);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.KEY_TYPE_OF_BUSINESS, "none");
        map.put(Constants.KEY_CONTACT, "1");
        map.put(Constants.KEY_NAME, "walmart");
        map.put(Constants.KEY_START_TIME, "11");
        map.put(Constants.KEY_END_TIME,"12");
        Store actualStore = storeService.addStore("Timbuktu","private","11","12","John Doe", new Merchant(), new Location());
        Store updatedStore = storeService.updateStore(actualStore, map);
        Assertions.assertEquals(store,updatedStore);
    }

    @Test
    public void testGetStoresByLocationAndMerchant(){
        List<Store> stores = new ArrayList<>();
        stores.add(new Store());
        stores.add(new Store());
        stores.add(new Store());

        Location loc = mock(Location.class);
        Merchant merchant = mock(Merchant.class);

        when(loc.getId()).thenReturn(1);
        when(merchant.getMerchantId()).thenReturn(2);

        when(mockedStoreRepository.findByLocationIdAndMerchantId(1,2)).thenReturn(stores);
        List<Store> result = storeService.getStoresByLocationAndMerchant(loc, merchant);
        Assertions.assertEquals(3,result.size());
    }

    @Test
    public void testGetStoresByMerchant(){
        List<Store> stores = new ArrayList<>();
        stores.add(new Store());
        stores.add(new Store());

        Merchant merchant = mock(Merchant.class);

        when(merchant.getMerchantId()).thenReturn(2);

        when(mockedStoreRepository.findByMerchantID(2)).thenReturn(stores);
        List<Store> result = storeService.getStoresByLocationAndMerchant(null,merchant);
        Assertions.assertEquals(2,result.size());
    }

    @Test
    public void testGetStoresByLocation(){
        List<Store> stores = new ArrayList<>();
        stores.add(new Store());

        Location loc = mock(Location.class);

        when(loc.getId()).thenReturn(1);

        when(mockedStoreRepository.findByLocationID(1)).thenReturn(stores);
        List<Store> result = storeService.getStoresByLocationAndMerchant(loc, null);
        Assertions.assertEquals(1,result.size());
    }

    @Test
    public void testGetStoresByNoLocationAndMerchant(){
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.getStoresByLocationAndMerchant(null, null));
        Assertions.assertEquals("Both location and merchant cannot be null",ex.getMessage());
    }
}
