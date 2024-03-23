package com.company.glovojpa.controller;

import com.company.glovojpa.controller.response.ApiResponse;
import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.model.Order;
import com.company.glovojpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private Order order1;

    @BeforeEach
    public void init() {
        Order order = new Order();
        order.setId(1);
        order.setDate(LocalDate.now());
        order.setCost(399.99);
        order.setProduct(List.of());
        order1 = orderRepository.save(order);
    }

    @Test
    void shouldCreateNewOrder() {
        String url = "http://localhost:" + port + "/api/v1/orders";
        HttpEntity<Order> request = new HttpEntity<>(order1);

        ResponseEntity<ApiResponse<OrderDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ApiResponse<OrderDto>>() {
                });
        ApiResponse<OrderDto> responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
    }

    @Test
    void shouldReturnOrderById() {
        String url = "http://localhost:" + port + "/api/v1/orders/" + order1.getId();

        ResponseEntity<ApiResponse<OrderDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<OrderDto>>() {
                });
        ApiResponse<OrderDto> responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
        assertNotNull(responseBody.getData());
        assertEquals(order1.getId(), responseBody.getData().getId());
    }

    @Test
    void shouldReturnOrders() {
        Order order2 = new Order();
        order2.setDate(LocalDate.now());
        order2.setCost(699.99);
        order2.setProduct(List.of());
        orderRepository.save(order2);
        String url = "http://localhost:" + port + "/api/v1/orders";

        ResponseEntity<ApiResponse<List<OrderDto>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<List<OrderDto>>>() {
                });
        ApiResponse<List<OrderDto>> responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
        assertNotNull(responseBody.getData());
    }

    @Test
    void shouldUpdateOrder() {
        Order updatedOrder = new Order();
        updatedOrder.setId(order1.getId());
        updatedOrder.setDate(LocalDate.now());
        updatedOrder.setCost(999.99);
        updatedOrder.setProduct(List.of());
        String url = "http://localhost:" + port + "/api/v1/orders/" + order1.getId();
        HttpEntity<Order> requestEntity = new HttpEntity<>(updatedOrder);

        ResponseEntity<ApiResponse<OrderDto>> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<OrderDto>>() {
                }
        );
        ApiResponse<OrderDto> responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
        assertNull(responseBody.getData());
    }

    @Test
    void shouldDeleteOrder() {
        String url = "http://localhost:" + port + "/api/v1/orders/" + order1.getId();

        ResponseEntity<ApiResponse<OrderDto>> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<ApiResponse<OrderDto>>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(orderRepository.existsById(order1.getId()));
    }
}