package com.berkaybarisalgun.orderservice.service;

import com.berkaybarisalgun.orderservice.dto.InventoryResponse;
import com.berkaybarisalgun.orderservice.dto.OrderLineItemsDto;
import com.berkaybarisalgun.orderservice.dto.OrderRequest;
import com.berkaybarisalgun.orderservice.model.Order;
import com.berkaybarisalgun.orderservice.model.OrderLineItems;
import com.berkaybarisalgun.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository repository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> list = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(list);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        // Call Inventory Service, and place order if product is in stock

        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInsStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        List<String> nonStockItems = Arrays.stream(inventoryResponsesArray)
                .filter(inventoryResponse ->!inventoryResponse.isInStock())
                .map(inventoryResponse -> inventoryResponse.getSkuCode())
                .collect(Collectors.toList());

        if(allProductsInsStock){
            repository.save(order);
        }
        else{
            String errorMessage = "Product(s) " + String.join(", ", nonStockItems) + " are not in stock, please try again later";
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
