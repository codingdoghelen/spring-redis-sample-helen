package com.sample.redis.spring.repository;


import com.sample.redis.spring.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableCaching
public class ProductDao {

    public static final String HASH_KEY = "Product";

    @Autowired
    private RedisTemplate template;

    @CacheEvict(value = "saveProducts", allEntries = true)
    public Product save(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    @Cacheable("findAllProducts")
    public List<Product> findAll() {
        System.out.println("Called findAllProducts from DB");

        return template.opsForHash().values(HASH_KEY);
    }


    @Cacheable(key="#id", value=HASH_KEY)
    public Product findProductById(int id) {
        System.out.println("Called findProductById: " + id + " from DB");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }


    public String deleteProduct(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "product removed !!";
    }
}
//
//{
//        "id": 101,
//        "name": "macbook",
//        "qty":1,
//        "price":1234
//        }