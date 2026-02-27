package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Book;
import com.example.demo.entity.CartItem;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public CartItem addToCart(Long bookId, Long userId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem existing = cartRepository.findByUserIdAndBookId(userId, bookId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepository.save(existing);
        }

        CartItem item = new CartItem();
        item.setBook(book);
        item.setUserId(userId);
        item.setQuantity(quantity);

        return cartRepository.save(item);
    }

    public List<CartItem> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void updateQuantity(Long cartItemId, int quantity) {
        CartItem item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartRepository.save(item);
        }
    }

    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }
}