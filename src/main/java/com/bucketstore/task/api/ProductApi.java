package com.bucketstore.task.api;

import com.bucketstore.task.core.dto.ProductResponse;
import com.bucketstore.task.core.service.ProductService;
import com.bucketstore.task.core.util.constants.ProductSortBy;
import com.bucketstore.task.core.util.constants.SortDirection;
import com.bucketstore.task.core.util.exception.InvalidRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "정렬 기준에 따른 상품 목록 조회")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 매개변수")
    public ResponseEntity<ProductResponse.MultipleProduct> getProducts(@Parameter(description = "정렬 기준 필드") @RequestParam(name = "sortBy", defaultValue = "productCode") String sortBy,
                                                                       @Parameter(description = "정렬 방향") @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
                                                                       @Parameter(description = "조회할 상품의 최대 개수") @RequestParam(name = "limit", defaultValue = "1") int limit) {

        //파라미터 유효성 검증
        validateParam(sortBy, sortDirection, limit);

        return ResponseEntity.ok(new ProductResponse.MultipleProduct(productService.getProducts(ProductSortBy.fromValue(sortBy).toString(),
                                                                                            SortDirection.fromValue(sortDirection).toString(),
                                                                                            limit)
                                                                               .orElse(Collections.emptyList())));
    }

    private void validateParam(String sortBy, String sortDirection, int limit) {
        // sortBy 유효성 검증
        if (!ProductSortBy.isValid(sortBy)) {
            throw new InvalidRequestException("Invalid sortBy value : " + sortBy);
        }

        // sortDirection 유효성 검증
        if (!SortDirection.isValid(sortDirection)) {
            throw new InvalidRequestException("Invalid sortDirection value : " + sortDirection);
        }

        // limit 유효성 검증
        if (limit < 0) {
            throw new InvalidRequestException("Invalid limit value : " + limit);
        }
    }

}
