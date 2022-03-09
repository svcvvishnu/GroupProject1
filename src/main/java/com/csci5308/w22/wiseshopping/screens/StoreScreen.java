package com.csci5308.w22.wiseshopping.screens;

import com.csci5308.w22.wiseshopping.exceptions.MenuInterruptedException;
import com.csci5308.w22.wiseshopping.factory.ScreenFactory;
import com.csci5308.w22.wiseshopping.models.Location;
import com.csci5308.w22.wiseshopping.models.Merchant;
import com.csci5308.w22.wiseshopping.models.Store;
import com.csci5308.w22.wiseshopping.models.User;
import com.csci5308.w22.wiseshopping.service.LocationService;
import com.csci5308.w22.wiseshopping.service.StoreService;
import com.csci5308.w22.wiseshopping.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Elizabeth James
 */
@Component
public class StoreScreen implements Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationScreen.class);
    private Scanner scanner;

    private StoreService storeService;
    private LocationService locationService;

    private ArrayList<String> validScreens;


//    private List<String> validScreens;

    private Merchant merchant;
    private User user;

    @Autowired
    public StoreScreen(Scanner scanner, StoreService storeService, LocationService locationService) {
        this.scanner = scanner;
        this.storeService = storeService;
        this.locationService = locationService;
//        validScreens= (ArrayList<String>) Arrays.asList("login", "dummy");
        validScreens= new ArrayList<>(Arrays.asList("login", "dummy"));
    }

    @Override
    public boolean render(ScreenFactory screenFactory) {
        LOGGER.info("****STORE MENU****");
        LOGGER.info("");
        boolean success = false;
        try {
            String input = "";
            LOGGER.info("enter one of the following \nadd \ndelete");
            input = scan(scanner);
            if (input.equals(Constants.ADD)) {
                LOGGER.info("Enter <store_name> <business_type> <start_time> <end_time> <contact> ");
                String storeName = scan(scanner);
                String businessType = scan(scanner);
                String startTime = scan(scanner);
                String endTime = scan(scanner);
                String contact = scan(scanner);
                LOGGER.info("Enter <locationName> <zipcode> <province> <country>");
                String locationName = scan(scanner);
                String zipcode = scan(scanner);
                String province = scan(scanner);
                String country = scan(scanner);
                Location location = locationService.addLocation(storeName, zipcode, province, country);
                Store store = storeService.addStore(locationName,businessType, startTime, endTime, contact,merchant,location);
                if (store!=null){
                    success = true;
                }

            } else if (input.equals(Constants.DELETE)) {
                List<Store> storeList = storeService.getAllStoresBelongingToAMerchant(merchant);
                storeList.stream().forEach(s -> LOGGER.info( "Store id:  {}, name: {}", s.getStoreId(), s.getStoreName()));
                String idToBeDeleted = scan(scanner);
                success = storeService.remove(Integer.parseInt(idToBeDeleted));

            }
        } catch (MenuInterruptedException e) {
            getNavigations(screenFactory, validScreens, LOGGER, scanner);
        }
        return success;
    }

    @Override
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public void setUser(User user) {
        this.user = user;

    }
}
