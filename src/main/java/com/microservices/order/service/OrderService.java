package com.microservices.order.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservices.order.client.InventoryClient;
import com.microservices.order.dto.OrderRequest;
import com.microservices.order.dto.UserDetails;
import com.microservices.order.event.OrderPlacedEvent;
import com.microservices.order.model.Order;
import com.microservices.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest, UserDetails userDetails){

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

        //Send message to Kafka Topic
        //order number, email
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), userDetails.getEmail());
        log.info("Start - Sending OrderPlcedEvent {} to Kafka topic order-placed", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("End - Sending OrderPlcedEvent {} to Kafka topic order-placed", orderPlacedEvent);

        }
        else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.getSkuCode() + " is not in stock");
        }
    }
}
