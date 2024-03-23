package com.company.glovojpa.service;

import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.mappers.OrderMapper;
import com.company.glovojpa.model.Order;
import com.company.glovojpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
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
        Order order = orderMapper.orderDtoToOrder(dto);
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(Integer id, OrderDto dto) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order oldOrder = optionalOrder.orElse(null);
        if (oldOrder == null) {
            throw new NoSuchElementException("Order not found");
        }
        Order newOrder = orderMapper.orderDtoToOrder(dto);
        orderMapper.update(oldOrder, newOrder);
        orderRepository.save(oldOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
