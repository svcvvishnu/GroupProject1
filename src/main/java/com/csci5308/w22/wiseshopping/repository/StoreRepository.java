package com.csci5308.w22.wiseshopping.repository;


import com.csci5308.w22.wiseshopping.models.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Elizabeth James
 */
@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {
    @Query(value = "SELECT * FROM store WHERE merchant_id = ?1", nativeQuery = true)
    List<Store> findByMerchantID(int merchantID);

    @Query(value = "SELECT * FROM store WHERE location_id = ?1", nativeQuery = true)
    List<Store> findByLocationID(int locationId);

    @Query(value = "SELECT * FROM store WHERE location_id = ?1 and merchant_id = ?2", nativeQuery = true)
    List<Store> findByLocationIdAndMerchantId(int locationId, int merchantId);

    Integer deleteByStoreId (int id);
}
