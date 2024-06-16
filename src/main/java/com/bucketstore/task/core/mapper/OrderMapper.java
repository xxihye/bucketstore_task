package com.bucketstore.task.core.mapper;

import com.bucketstore.task.core.dto.OrderItem;
import com.bucketstore.task.core.dto.OrderItemDetail;
import com.bucketstore.task.core.entity.order.Order;
import com.bucketstore.task.core.entity.order.OrderProduct;
import com.bucketstore.task.core.util.constants.OrderStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders (order_no, order_date, user_id, shipping_address, postal_code, recipient, shipping_cost, total_amount) " +
            "VALUES (#{orderNo}, NOW(), #{userId}, #{shippingAddress}, #{postalCode}, #{recipient}, #{shippingCost}, #{totalAmount})")
    @Options(useGeneratedKeys = true, keyProperty = "orderNo")
    int insertOrder(Order order);

    @Insert("INSERT INTO order_products (order_no, product_option_no, order_status, price, quantity) " +
            "VALUES (#{orderNo}, #{productOptionNo}, #{orderStatus}, #{price}, #{quantity})")
    int insertOrderProduct(OrderProduct orderProduct);

    @Select("SELECT * FROM order_products WHERE order_product_no = ${orderProductNo}")
    OrderProduct findOrderProductByOrderProductNo(Long orderProductNo);

    @Update("UPDATE order_products SET order_status = #{orderStatus} WHERE order_product_no = #{orderProductNo}")
    int updateOrderStatus(@Param("orderProductNo") Long orderProductNo, OrderStatus orderStatus);

    @Select("SELECT * FROM orders WHERE order_no = #{orderNo}")
    Order findOrderByOrderNo(Long orderNo);

    @Update("UPDATE orders SET total_amount = #{newTotalAmount} WHERE order_no = #{orderNo}")
    int updateTotalAmount(Long orderNo, int newTotalAmount);

    @Select("SELECT * from order_products WHERE order_no = #{orderNo}")
    List<OrderProduct> findOrderProductsByOrderNo(Long orderNo);

    @Select("SELECT * FROM orders ORDER BY ${sortBy} ${sortDirection} LIMIT ${limit}")
    List<Order> findOrders(@Param("sortBy") String sortBy, @Param("sortDirection") String sortDirection, @Param("limit") int limit);

    @Select("SELECT p.product_code AS product_code," +
            " p.product_name AS product_name," +
            " op.price AS price," +
            " po.size AS size," +
            " op.quantity AS quantity " +
            "FROM order_products op JOIN product_options po ON op.product_option_no = po.product_option_no " +
            "   JOIN products p ON p.product_code = po.product_code " +
            "WHERE op.order_no = #{orderNo}")
    List<OrderItem> findOrderItemsByOrderNo(Long orderNo);


    @Select("SELECT p.product_code AS product_code," +
            " p.product_name AS product_name," +
            " op.price AS price," +
            " po.size AS size," +
            " op.quantity AS quantity, " +
            " op.order_status AS order_status, " +
            " 0 as deductedShippingCost " +
            "FROM order_products op JOIN product_options po ON op.product_option_no = po.product_option_no " +
            "   JOIN products p ON p.product_code = po.product_code " +
            "WHERE op.order_no = #{orderNo}")
    List<OrderItemDetail> findOrderItemDetailsByOrderNo(Long orderNo);
}
