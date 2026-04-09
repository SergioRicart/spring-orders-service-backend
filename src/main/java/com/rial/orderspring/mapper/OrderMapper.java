package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.response.OrderProductResponse;
import com.rial.orderspring.dto.request.OrderRequest;
import com.rial.orderspring.dto.response.OrderResponse;
import com.rial.orderspring.exception.ClientNotFoundException;
import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.model.Order;
import com.rial.orderspring.model.OrderProduct;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.repository.ClientRepository;
import com.rial.orderspring.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderMapper {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public OrderMapper(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDateTime(order.getOrderDateTime());
        response.setDeliveryDateTime(order.getDeliveryDateTime());
        response.setPaymentState(order.getPaymentState());
        response.setOrderState(order.getOrderState());
        response.setClientId(order.getClient() != null ? order.getClient().getId() : null);

        if (order.getOrderProducts() != null) {
            List<OrderProductResponse> products = order.getOrderProducts().stream()
                    .map(op -> {
                        OrderProductResponse opResponse = new OrderProductResponse();
                        opResponse.setId(op.getId());
                        opResponse.setProductId(op.getProduct() != null ? op.getProduct().getId() : null);
                        opResponse.setQuantity(op.getQuantity());
                        return opResponse;
                    }).toList();
            response.setOrderProducts(products);
        } else {
            response.setOrderProducts(Collections.emptyList());
        }

        return response;
    }

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setOrderDateTime(request.getOrderDateTime());
        order.setDeliveryDateTime(request.getDeliveryDateTime());
        order.setPaymentState(request.getPaymentState());
        order.setOrderState(request.getOrderState());

        if (request.getClientId() != null) {
            order.setClient(clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ClientNotFoundException(request.getClientId())));
        }

        if (request.getOrderProducts() != null) {
            List<OrderProduct> orderProducts = request.getOrderProducts().stream()
                    .map(opRequest -> {
                        OrderProduct op = new OrderProduct();
                        op.setQuantity(opRequest.getQuantity());
                        op.setOrder(order);
                        if (opRequest.getProductId() != null) {
                            Product product = productRepository.findById(opRequest.getProductId())
                                    .orElseThrow(() -> new ProductNotFoundException(opRequest.getProductId()));
                            op.setProduct(product);
                        }
                        return op;
                    }).toList();
            order.setOrderProducts(orderProducts);
        }

        return order;
    }
}
