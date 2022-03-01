package com.csci5308.w22.wiseshopping.service;

import com.csci5308.w22.wiseshopping.exceptions.UserAlreadyRegisteredException;
import com.csci5308.w22.wiseshopping.models.*;
import com.csci5308.w22.wiseshopping.repository.MerchantRepository;
import com.csci5308.w22.wiseshopping.repository.ProductCategoryRepository;
import com.csci5308.w22.wiseshopping.repository.ProductInventoryRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author Elizabeth James
 */

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;
    ProductInventoryRepository productInventoryRepository;
    ProductCategoryRepository productCategoryRepository;
    /**
     * inserts a merchant into table
     * @param name name of merchant
     * @param email email of merchant
     * @param password password of merchant
     * @return true if success, else false
     */
    @Transactional
    public Merchant registerMerchant(String name, String email, String password) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty or blank");
        }
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("password cannot be null or empty or blank");
        }

        if (email == null || email.isBlank() || email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty or blank");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("given email is not valid");
        }

        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        if (merchant!=null){
            throw new UserAlreadyRegisteredException(email + " is already registered");
        }
        merchant = new Merchant(name, email, password);
        merchantRepository.save(merchant);
        return merchant;
    }

    /**
     * deletes a store from table
     * @param email email of the merchant
     * @return true, if success; else false
     */
    @Transactional
    public boolean removeMerchant(String email) {
        if (email == null || email.isBlank() || email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty or blank");
        }
        int id = merchantRepository.deleteByEmail(email);
        if (id > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public Merchant loginMerchant(String email, String password) {
        if (email == null) {
            throw new NullPointerException("email cannot be null");
        }
        if (email.isEmpty() || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be empty");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("given email id is not valid");
        }

        if(password==null)
        {
            throw new NullPointerException("password cannot be null");
        }
        if(password.isBlank() || password.isEmpty())
        {
            throw new IllegalArgumentException("password cannot be empty");
        }

        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        return merchant;


    }


    public Merchant getMerchantByEmail(String email){
        return merchantRepository.findMerchantByEmail(email);
    }

    @Transactional
    public ProductInventory updateProductPrice(Product product, Store store, int price) {

        ProductInventory productInventory = productInventoryRepository.findByProductAndStore(product, store);

        if (productInventory == null) {
            throw new IllegalArgumentException("Could not find inventory with given Product in store");
        }

        productInventory.setPrice(price);
        productInventoryRepository.save(productInventory);
        return productInventory;
    }

    @Transactional
    public ProductInventory updateProductStock(Product product, Store store, int stock) {
        ProductInventory productInventory = productInventoryRepository.findByProductAndStore(product, store);

        if (productInventory == null) {
            throw new IllegalArgumentException("Could not find inventory with given Product in store:");
        }

        productInventory.setStock(stock);
        productInventoryRepository.save(productInventory);
        return productInventory;
    }

    @Transactional
    public ProductCategory updateProductCategoryName(int productCategoryId, String name) {
        ProductCategory category = productCategoryRepository.findByProductCategoryId(productCategoryId);

        if (category == null) {
            throw new IllegalArgumentException("Could not find category with given Id: " + productCategoryId);
        }

        category.setCategoryName(name);
        productCategoryRepository.save(category);
        return category;
    }

    @Transactional
    public ProductCategory updateProductCategoryDescription(int productCategoryId, String description) {
        ProductCategory category = productCategoryRepository.findByProductCategoryId(productCategoryId);

        if (category == null) {
            throw new IllegalArgumentException("Could not find category with given Id: " + productCategoryId);
        }

        category.setCategoryDesc(description);
        productCategoryRepository.save(category);
        return category;
    }

}


