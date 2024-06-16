package com.bucketstore.task.core.mapper;

import com.bucketstore.task.core.entity.product.Product;
import com.bucketstore.task.core.entity.product.ProductOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products ORDER BY ${sortBy} ${sortDirection} LIMIT ${limit}")
    List<Product> findProducts(@Param("sortBy") String sortBy, @Param("sortDirection") String sortDirection, @Param("limit") int limit);

    @Select("SELECT po.* FROM products p JOIN product_options po ON p.product_code = po.product_code WHERE p.product_code = #{productCode}")
    List<ProductOption> findProductOptionAllByProductCode(@Param("productCode") String productCode);

    @Select("SELECT * FROM products WHERE product_code = #{productCode}")
    Product findByProductCode(String productCode);

    @Select("SELECT * FROM product_options WHERE product_code = #{productCode} AND size = #{size}")
    ProductOption findProductOptionByCodeAndSize(String productCode, String size);

    @Update("UPDATE product_options SET stock = stock - #{quantity} WHERE product_option_no = #{productOptionNo}")
    int updateStock(@Param("productOptionNo") Long productOptionNo, int quantity);
}
