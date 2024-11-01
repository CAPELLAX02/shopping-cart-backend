package com.capellax.shoppingCart.service.order;

import com.capellax.shoppingCart.dto.OrderDTO;
import com.capellax.shoppingCart.enums.OrderStatus;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.Cart;
import com.capellax.shoppingCart.model.Order;
import com.capellax.shoppingCart.model.OrderItem;
import com.capellax.shoppingCart.model.Product;
import com.capellax.shoppingCart.repository.OrderRepository;
import com.capellax.shoppingCart.repository.ProductRepository;
import com.capellax.shoppingCart.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(
            Long userId
    ) {
        Cart cart = cartService.getCart(userId);
        Order order = createOrder(cart);

        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);

        return savedOrder;
    }

    private Order createOrder(
            Cart cart
    ) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(
            Order order,
            Cart cart
    ) {
        return cart.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                })
                .toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDTO getOrder(
            Long orderId
    ) {
        return orderRepository
                .findById(orderId)
                .map(this::converOrderToOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDTO> getUserOrders(
            Long userId
    ) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::converOrderToOrderDTO)
                .toList();
    }

    private OrderDTO converOrderToOrderDTO(
            Order order
    ) {
        return modelMapper.map(order, OrderDTO.class);
    }

}
