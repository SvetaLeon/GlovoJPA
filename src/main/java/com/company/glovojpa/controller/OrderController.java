package com.company.glovojpa.controller;

import com.company.glovojpa.controller.response.ApiResponse;
import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders(
            @RequestParam(value = "pageNum", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
            int pageNum,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
            String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                    Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
            List<OrderDto> result = orderService.getOrders(pageable);
            return ResponseEntity.ok(new ApiResponse<>(true, result, Collections.emptyList()));
        } catch (Exception e) {
            log.error("Failed to retrieve orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null,
                    Collections.singletonList("Failed to retrieve orders")));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Integer id) {
        try {
            OrderDto orderDto = orderService.getOrderById(id);
            if (orderDto != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, orderDto, Collections.emptyList()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null,
                        Collections.singletonList("Order not found")));
            }
        } catch (Exception e) {
            log.error("Failed to retrieve order with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null,
                    Collections.singletonList("Failed to retrieve order")));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderDto>> createNewOrder(@RequestBody OrderDto dto) {
        try {
            orderService.saveNewOrder(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, null,
                    Collections.singletonList("Order created")));
        } catch (Exception e) {
            log.error("Failed to create order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null,
                    Collections.singletonList("Failed to create order")));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderById(@PathVariable Integer id, @RequestBody OrderDto dto) {
        try {
            orderService.updateOrder(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, null, Collections.singletonList("Order updated")));
        } catch (Exception e) {
            log.error("Failed to update order with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null,
                    Collections.singletonList("Failed to update order")));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> deleteOrderById(@PathVariable Integer id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(new ApiResponse<>(true, null, Collections.singletonList("Order deleted")));
        } catch (Exception e) {
            log.error("Failed to delete order with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null,
                    Collections.singletonList("Failed to delete order")));
        }
    }
}