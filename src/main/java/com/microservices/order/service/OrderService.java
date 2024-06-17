package com.microservices.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.microservices.order.client.InventoryClient;
import com.microservices.order.dto.OrderRequest;
import com.microservices.order.model.Order;
import com.microservices.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity());

        if (isProductInStock){
        //map OrderRequest to Order object
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.getPrice());
        order.setSkuCode(orderRequest.getSkuCode());
        order.setQuantity(orderRequest.getQuantity());

        //save order to OrderRepository
        orderRepository.save(order);
        }
        else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.getSkuCode() + " is not in stock");
        }
    }
}
