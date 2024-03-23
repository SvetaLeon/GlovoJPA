package com.company.glovojpa.service;

import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.mappers.OrderMapper;
import com.company.glovojpa.model.Order;
import com.company.glovojpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final int ORDER_ID = 1;
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @InjectMocks
    private OrderServiceImpl testInstance;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private Page<Order> page;

    @Mock
    private List<Order> orders;

    @Mock
    private List<OrderDto> orderDtoList;

    @Mock
    private Order order;

    @Mock
    private Order newOrder;

    private OrderDto dto;

    @BeforeEach
    public void init() {
        dto = new OrderDto();
        dto.setId(ORDER_ID);
        dto.setDate(LocalDate.now());
        dto.setCost(10.55);
        dto.setProducts(null);
    }

    @Test
    void shouldReturnOrders() {
        when(orderRepository.findAll(PAGEABLE)).thenReturn(page);
        when(page.getContent()).thenReturn(orders);
        when(orderMapper.toOrderDtoList(orders)).thenReturn(orderDtoList);

        List<OrderDto> result = testInstance.getOrders(PAGEABLE);

        verify(orderRepository, times(1)).findAll(PAGEABLE);
        verify(orderMapper, times(1)).toOrderDtoList(orders);
        assertNotNull(result);
        assertEquals(orderDtoList, result);
    }

    @Test
    void shouldReturnOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderDto(order)).thenReturn(dto);

        OrderDto result = testInstance.getOrderById(ORDER_ID);

        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderMapper, times(1)).orderToOrderDto(order);
        assertNotNull(result);
        assertEquals(ORDER_ID, result.getId());
    }

    @Test
    void shouldNotReturnOrderById() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            testInstance.getOrderById(ORDER_ID);
        });
    }

    @Test
    void shouldCreateNewOrder() {
        when(orderMapper.orderDtoToOrder(dto)).thenReturn(order);

        testInstance.saveNewOrder(dto);

        verify(orderMapper, times(1)).orderDtoToOrder(dto);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldUpdateOrder() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderMapper.orderDtoToOrder(dto)).thenReturn(newOrder);

        testInstance.updateOrder(ORDER_ID, dto);

        verify(orderRepository, times(1)).findById(ORDER_ID);
        verify(orderMapper, times(1)).orderDtoToOrder(dto);
        verify(orderMapper, times(1)).update(order, newOrder);
        verify(orderRepository, times(1)).save(order);
        assertNotNull(order.getId());
    }

    @Test
    void shouldNotUpdateOrder() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            testInstance.updateOrder(ORDER_ID, dto);
        });
    }

    @Test
    void shouldDeleteOrder() {
        testInstance.deleteOrder(ORDER_ID);

        verify(orderRepository, times(1)).deleteById(ORDER_ID);
    }
}