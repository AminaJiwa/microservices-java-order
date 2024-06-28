package com.microservices.order.dto;

import java.math.BigDecimal;

public class OrderRequest {
    private Long id;
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public OrderRequest(Long id, String orderNumber, String skuCode,
    BigDecimal price, Integer quantity){
        this.id = id;
        this.orderNumber = orderNumber;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

    //Getters
    public Long getId(){
        return id;
    }

    public String getOrderNumber(){
        return orderNumber;
    }

    public String getSkuCode(){
        return skuCode;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public Integer getQuantity(){
        return quantity;
    }

    //Setters
    public void setId(Long id){
        this.id = id;
    }

    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }

    public void setSkuCode(String skuCode){
        this.skuCode = skuCode;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    
}
