package com.example.ecommerce.orders;

import com.example.ecommerce.auth.AuthService;
import com.example.ecommerce.carts.CartEmptyException;
import com.example.ecommerce.carts.CartNotFoundException;
import com.example.ecommerce.carts.CartRepository;
import com.example.ecommerce.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        // No real payment gateway is wired up here — the order is marked PAID
        // immediately for learning purposes. In a real system, this status would
        // only change once a payment provider (e.g. Stripe, Razorpay) confirms
        // the payment, usually through a webhook callback.
        order.setStatus(PaymentStatus.PAID);

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return orderMapper.toDto(order);
    }
}
