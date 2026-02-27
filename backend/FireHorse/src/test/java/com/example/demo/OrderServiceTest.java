package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Book;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceOrder_DecrementsStock() {
        // Arrange
        Long userId = 1L;
        Long bookId = 100L;
        int initialStock = 15;
        int quantityToBuy = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setStock(initialStock);
        book.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantityToBuy);
        cartItem.setUserId(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(Collections.singletonList(cartItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        orderService.placeOrder(userId);

        // Assert
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookCaptor.capture());

        Book savedBook = bookCaptor.getValue();
        assertEquals(14, savedBook.getStock(), "Stock should be decremented by 1");
    }
}
