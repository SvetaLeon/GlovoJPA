package com.company.glovojpa.mappers;

import com.company.glovojpa.dto.ProductDto;
import com.company.glovojpa.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto dto);

    List<ProductDto> toProductDtoList(List<Product> products);

    List<Product> toProductList(List<ProductDto> dtos);
}
