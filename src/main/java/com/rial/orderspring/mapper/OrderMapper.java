package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.OrderDTO;
import com.rial.orderspring.dto.OrderProductDTO;
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

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDateTime(order.getOrderDateTime());
        dto.setDeliveryDateTime(order.getDeliveryDateTime());
        dto.setPaymentState(order.getPaymentState());
        dto.setOrderState(order.getOrderState());
        dto.setClientId(order.getClient() != null ? order.getClient().getId() : null);

        if (order.getOrderProducts() != null) {
            List<OrderProductDTO> products = order.getOrderProducts().stream()
                    .map(op -> {
                        OrderProductDTO opDto = new OrderProductDTO();
                        opDto.setId(op.getId());
                        opDto.setProductId(op.getProduct() != null ? op.getProduct().getId() : null);
                        opDto.setQuantity(op.getQuantity());
                        return opDto;
                    }).toList();
            dto.setOrderProducts(products);
        } else {
            dto.setOrderProducts(Collections.emptyList());
        }

        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDateTime(dto.getOrderDateTime());
        order.setDeliveryDateTime(dto.getDeliveryDateTime());
        order.setPaymentState(dto.getPaymentState());
        order.setOrderState(dto.getOrderState());

        if (dto.getClientId() != null) {
            order.setClient(clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ClientNotFoundException(dto.getClientId())));
        }

        if (dto.getOrderProducts() != null) {
            List<OrderProduct> orderProducts = dto.getOrderProducts().stream()
                    .map(opDto -> {
                        OrderProduct op = new OrderProduct();
                        op.setId(opDto.getId());
                        op.setQuantity(opDto.getQuantity());
                        op.setOrder(order);
                        if (opDto.getProductId() != null) {
                            Product product = productRepository.findById(opDto.getProductId())
                                    .orElseThrow(() -> new ProductNotFoundException(opDto.getProductId()));
                            op.setProduct(product);
                        }
                        return op;
                    }).toList();
            order.setOrderProducts(orderProducts);
        }

        return order;
    }
}
