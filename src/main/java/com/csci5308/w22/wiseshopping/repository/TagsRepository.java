package com.csci5308.w22.wiseshopping.repository;

import com.csci5308.w22.wiseshopping.models.ProductCategory;
import com.csci5308.w22.wiseshopping.models.ProductInventory;
import com.csci5308.w22.wiseshopping.models.Tags;
import com.csci5308.w22.wiseshopping.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nilesh
 */
@Repository
public interface TagsRepository extends CrudRepository<Tags,Integer> {

    Tags findByTagName(String name);

}
