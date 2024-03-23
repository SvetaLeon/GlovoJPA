package com.company.glovojpa.mappers;

import com.company.glovojpa.dto.OrderDto;
import com.company.glovojpa.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "product", source = "products")
    Order orderDtoToOrder(OrderDto dto);

    @Mapping(target = "products", source = "product")
    OrderDto orderToOrderDto(Order order);

    List<OrderDto> toOrderDtoList(Iterable<Order> orders);

    void update(@MappingTarget Order target, Order source);
}
