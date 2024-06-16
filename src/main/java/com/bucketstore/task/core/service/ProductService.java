package com.bucketstore.task.core.service;


import com.bucketstore.task.core.dto.ProductResponse;
import com.bucketstore.task.core.entity.product.Product;
import com.bucketstore.task.core.entity.product.ProductOption;
import com.bucketstore.task.core.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public Optional<List<ProductResponse>> getProducts(String sortBy, String sortDirection, int limit) {
        List<Product> products = productMapper.findProducts(sortBy, sortDirection, limit);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(products.stream()
                                    .map(this::convertToProductDto)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .collect(Collectors.toList()));
    }

    private Optional<ProductResponse> convertToProductDto(Product product) {
        List<ProductOption> options = productMapper.findProductOptionAllByProductCode(product.getProductCode());

        if (options == null) {
            return Optional.empty();
        }

        return Optional.of(ProductResponse.from(product, options));
    }
}
