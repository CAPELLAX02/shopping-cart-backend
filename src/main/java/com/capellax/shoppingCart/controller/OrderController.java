package com.capellax.shoppingCart.controller;

import com.capellax.shoppingCart.dto.OrderDTO;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.Order;
import com.capellax.shoppingCart.response.ApiResponse;
import com.capellax.shoppingCart.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService; // IOrderService ???

    @PostMapping("/placeorder")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestParam Long userId
    ) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Place Order Success", order));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Interval Server Error!", null));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(
            @PathVariable Long orderId
    ) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Get Order Success", order));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Order not found", null));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(
            @PathVariable Long userId
    ) {
        try {
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Get User Orders Success", orders));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Orders not found", null));
        }
    }

}
