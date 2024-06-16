package com.bucketstore.task.api;

import com.bucketstore.task.core.dto.OrderDetailResponse;
import com.bucketstore.task.core.dto.OrderRequest;
import com.bucketstore.task.core.dto.OrderResponse;
import com.bucketstore.task.core.service.OrderService;
import com.bucketstore.task.core.util.constants.OrderSortBy;
import com.bucketstore.task.core.util.constants.SortDirection;
import com.bucketstore.task.core.util.exception.InvalidRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 입력", description = "입력된 상품 정보들로 주문 입력 ")
    @ApiResponse(responseCode = "200", description = "주문 성공")
    @ApiResponse(responseCode = "404", description = "상품 및 옵션을 찾을 수 없음")
    @ApiResponse(responseCode = "409", description = "재고 부족")
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);

        return ResponseEntity.ok("Order created successfully ");
    }

    @PutMapping("/{orderProductNo}/cancel")
    @Operation(summary = "주문 부분 취소", description = "주문 식별자로 특정 주문 내 상품 취소")
    @ApiResponse(responseCode = "200", description = "주문 취소 성공")
    @ApiResponse(responseCode = "404", description = "주문 상품을 찾을 수 없음")
    @ApiResponse(responseCode = "405", description = "취소 불가능한 주문 상태")
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    public ResponseEntity<String> cancelOrderProduct(@Parameter(description = "주문 상품 식별자") @PathVariable Long orderProductNo){
        orderService.cancelOrderProduct(orderProductNo);

        return ResponseEntity.ok("Order canceled successfully");
    }

    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "정렬 기준에 따른 주문 목록 조회")
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 매개변수")
    public ResponseEntity<OrderResponse.MultipleOrder> getOrders(@Parameter(description = "정렬 기준 필드") @RequestParam(name = "sortBy", defaultValue = "orderNo") String sortBy,
                                                                 @Parameter(description = "정렬 방향") @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
                                                                 @Parameter(description = "조회 개수") @RequestParam(name = "limit", defaultValue = "1") int limit){

        //파라미터 유효성 검증
        validateParam(sortBy, sortDirection, limit);

        return ResponseEntity.ok(new OrderResponse.MultipleOrder(orderService.getOrders(OrderSortBy.fromValue(sortBy).toString(),
                                                                                        SortDirection.fromValue(sortDirection).toString(),
                                                                                        limit)
                                                                             .orElse(Collections.emptyList())));
    }

    private void validateParam(String sortBy, String sortDirection, int limit) {
        // sortBy 유효성 검증
        if (!OrderSortBy.isValid(sortBy)) {
            throw new InvalidRequestException("Invalid sortBy value");
        }

        // sortDirection 유효성 검증
        if (!SortDirection.isValid(sortDirection)) {
            throw new InvalidRequestException("Invalid sortDirection value");
        }

        // limit 유효성 검증
        if (limit < 0) {
            throw new InvalidRequestException("Invalid limit value");
        }
    }

    @GetMapping("/{orderNo}")
    @Operation(summary = "주문 단건 조회", description = "주문 식별자로 특정 주문 정보 조회")
    @ApiResponse(responseCode = "200", description = "주문 조회 성공")
    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
    public ResponseEntity<OrderDetailResponse> getOrder(@Parameter(description = "주문 식별자") @PathVariable Long orderNo){
        return ResponseEntity.ok(orderService.getOrder(orderNo));
    }
}
