package com.capellax.shoppingCart.service.order;

import com.capellax.shoppingCart.dto.OrderDTO;
import com.capellax.shoppingCart.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

}
