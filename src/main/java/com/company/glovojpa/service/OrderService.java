package com.company.glovojpa.service;

import com.company.glovojpa.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders(Pageable pageable);

    OrderDto getOrderById(Integer id);

    void saveNewOrder(OrderDto dto);

    void updateOrder(Integer id, OrderDto dto);

    void deleteOrder(Integer id);
}
