package com.company.glovojpa.service;

import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.mappers.OrderMapper;
import com.company.glovojpa.model.Order;
import com.company.glovojpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> orders = page.getContent();
        return orderMapper.toOrderDtoList(orders);

    }

    @Override
    public OrderDto getOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElse(null);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public void saveNewOrder(OrderDto dto) {
        log.debug("Saving new order: {}", dto);
        Order order = orderMapper.orderDtoToOrder(dto);
        Order saved = orderRepository.save(order);
        log.info("Order saved in the database: {}", saved);
    }

    @Override
    public void updateOrder(Integer id, OrderDto dto) {
        log.debug("Updating order with ID: {}", id);
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order oldOrder = optionalOrder.orElse(null);
        if (oldOrder == null) {
            throw new NoSuchElementException("Order not found");
        }
        log.debug("Old order found in the database: {}", oldOrder);
        Order newOrder = orderMapper.orderDtoToOrder(dto);
        orderMapper.update(oldOrder, newOrder);
        orderRepository.save(oldOrder);
        log.info("Order with ID {} updated successfully", id);
    }

    @Override
    public void deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("Order with ID {} deleted successfully", id);
        } else {
            throw new NoSuchElementException("Order not found");
        }
    }
}
