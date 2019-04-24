package com.dao.product;

import com.dao.BaseMapper;
import com.model.product.Product;
import com.model.product.ProductSub;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 */

public interface ProductMapper extends BaseMapper<Product, ProductSub> {

    List<Map<String,Object>> findProductPageByBrandId(Integer brandId);

    List<Map<String,Object>> findProductPageByKeyword(String keyword);
}