package com.example.mapper.checkout;

import com.example.dto.checkout.CheckoutDto;
import com.example.dto.checkout.CheckoutProductDto;
import com.example.dto.checkout.OrderDto;
import com.example.dto.checkout.RecipientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CheckoutMapperImp implements CheckoutMapper {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public CheckoutDto setCheckout(CheckoutDto checkoutDto) {

        String sql = "INSERT INTO checkout (prime,status_code) VALUE (:prime,:status_code);";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameters.addValue("prime", checkoutDto.getPrime());
        parameters.addValue("status_code", 0);// waiting for confirm
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        checkoutDto.setId(keyHolder.getKey().intValue());
        return checkoutDto;
    }

    @Override
    public CheckoutDto setOrder(CheckoutDto checkoutDto) {
        // get OrderDto object
        OrderDto orderDto = checkoutDto.getOrderDto();
        // set database
        String sql = "INSERT INTO order_info (checkout_id,shipping,payment,subtotal,freight,total,user_id) VALUE (:checkout_id,:shipping,:payment,:subtotal,:freight,:total,:user_id);";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameters.addValue("checkout_id", orderDto.getCheckoutId());
        parameters.addValue("shipping", orderDto.getShipping());
        parameters.addValue("payment", orderDto.getPayment());
        parameters.addValue("subtotal", orderDto.getSubtotal());
        parameters.addValue("freight", orderDto.getFreight());
        parameters.addValue("total", orderDto.getTotal());
        parameters.addValue("user_id", orderDto.getUserId());
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        orderDto.setId(keyHolder.getKey().intValue());
        return checkoutDto;
    }

    @Override
    public CheckoutDto setRecipient(CheckoutDto checkoutDto) {
        // get RecipientDto object
        RecipientDto recipientDto = checkoutDto.getOrderDto().getRecipientDto();
        // set database
        String sql = "INSERT INTO recipient (order_id,name,phone,email,address,time) VALUE (:order_id,:name,:phone,:email,:address,:time);";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameters.addValue("order_id", recipientDto.getOrderId());
        parameters.addValue("name", recipientDto.getName());
        parameters.addValue("phone", recipientDto.getPhone());
        parameters.addValue("email", recipientDto.getEmail());
        parameters.addValue("address", recipientDto.getAddress());
        parameters.addValue("time", recipientDto.getTime());
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        recipientDto.setId(keyHolder.getKey().intValue());
        return checkoutDto;
    }

    @Override
    public CheckoutDto setList(CheckoutDto checkoutDto) {
        // get RecipientDto object
        List<CheckoutProductDto> checkoutProductDtos = checkoutDto.getOrderDto().getList();
        // set database
        for (CheckoutProductDto checkoutProductDto : checkoutProductDtos) {
            String sql = "INSERT INTO product_list (order_id,product_id,name,price,color_id,size,qty) VALUE (:order_id,:product_id,:name,:price,:color_id,:size,:qty);";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            parameters.addValue("order_id", checkoutProductDto.getOrderId());
            parameters.addValue("product_id", checkoutProductDto.getProductId());
            parameters.addValue("name", checkoutProductDto.getName());
            parameters.addValue("price", checkoutProductDto.getPrice());
            parameters.addValue("color_id", checkoutProductDto.getColorId());
            parameters.addValue("size", checkoutProductDto.getSize());
            parameters.addValue("qty", checkoutProductDto.getQty());
            namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
            checkoutProductDto.setId(keyHolder.getKey().intValue());
        }
        return checkoutDto;
    }

    @Override
    public void confirmCheckoutStatus(CheckoutDto checkoutDto, int statusCode) {
        String sql = "UPDATE checkout SET status_code = :status_code WHERE id = :id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("status_code", statusCode);
        parameters.addValue("id", checkoutDto.getId());// waiting for confirm
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public CheckoutDto getCheckout(int checkoutId) {
        String sql = "SELECT * FROM checkout WHERE id = :id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", checkoutId);
        List<CheckoutDto> checkoutDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<CheckoutDto>) (rs, rowNum) -> {
            CheckoutDto checkoutDto = new CheckoutDto();
            checkoutDto.setId(rs.getInt("id"));
            checkoutDto.setPrime(rs.getString("prime"));
            return checkoutDto;
        });
        return (checkoutDtos.size() > 0) ? checkoutDtos.get(0) : null;
    }

    @Override
    public OrderDto getOrder(int checkoutId) {
        String sql = "SELECT * FROM order_info WHERE checkout_id = :checkout_id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("checkout_id", checkoutId);
        List<OrderDto> orderDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<OrderDto>) (rs, rowNum) -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(rs.getInt("id"));
            orderDto.setCheckoutId(rs.getInt("checkout_id"));
            orderDto.setShipping(rs.getString("shipping"));
            orderDto.setPayment(rs.getString("payment"));
            orderDto.setSubtotal(rs.getInt("subtotal"));
            orderDto.setFreight(rs.getInt("freight"));
            orderDto.setTotal(rs.getInt("total"));
            orderDto.setUserId(rs.getInt("user_id"));
            return orderDto;
        });
        return (orderDtos.size() > 0) ? orderDtos.get(0) : null;
    }

    @Override
    public RecipientDto getRecipient(int orderId) {
        String sql = "SELECT * FROM recipient WHERE order_id = :order_id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderId);
        List<RecipientDto> recipientDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<RecipientDto>) (rs, rowNum) -> {
            RecipientDto recipientDto = new RecipientDto();
            recipientDto.setId(rs.getInt("id"));
            recipientDto.setOrderId(rs.getInt("order_id"));
            recipientDto.setName(rs.getString("name"));
            recipientDto.setPhone(rs.getString("phone"));
            recipientDto.setEmail(rs.getString("email"));
            recipientDto.setAddress(rs.getString("address"));
            recipientDto.setTime(rs.getString("time"));
            return recipientDto;
        });
        return (recipientDtos.size() > 0) ? recipientDtos.get(0) : null;
    }

    @Override
    public CheckoutProductDto getList(int orderId) {
        String sql = "SELECT * FROM product_list WHERE order_id = :order_id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderId);
        List<CheckoutProductDto> recipientDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<CheckoutProductDto>) (rs, rowNum) -> {
            CheckoutProductDto checkoutProductDto = new CheckoutProductDto();
            checkoutProductDto.setId(rs.getLong("id"));
            checkoutProductDto.setProductId(rs.getInt("product_id"));
            checkoutProductDto.setName(rs.getString("name"));
            checkoutProductDto.setPrice(rs.getInt("price"));
            checkoutProductDto.setColorId(rs.getInt("color_id"));
            checkoutProductDto.setSize(rs.getString("size"));
            checkoutProductDto.setQty(rs.getInt("qty"));
            return checkoutProductDto;
        });
        return (recipientDtos.size() > 0) ? recipientDtos.get(0) : null;
    }
}
