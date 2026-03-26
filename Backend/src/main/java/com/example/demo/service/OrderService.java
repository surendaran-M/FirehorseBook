package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Booksentity;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Ordersentity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Ordersentity placeOrder(int userId) { // Changed to int
        
        List<CartItem> cartItems = cartRepository.findByUserId(userId); // Now uses int

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Booksentity book = item.getBook();
            if (book.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
            BigDecimal price = book.getPrice();
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        for (CartItem item : cartItems) {
            Booksentity book = item.getBook();
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(book.getPrice());
            orderItem.setTitle(book.getTitle());
            orderItems.add(orderItem);
        }

        Ordersentity order = new Ordersentity();
        order.setUserId(String.valueOf(userId)); // Convert int to String for Ordersentity
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("pending");
        order.setItems(orderItems);

        orderItems.forEach(oi -> oi.setOrder(order));
        Ordersentity savedOrder = orderRepository.save(order);
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Ordersentity> getOrdersByUser(int userId) { // Changed to int
        return orderRepository.findByUserId(String.valueOf(userId)); // Convert int to String
    }
}