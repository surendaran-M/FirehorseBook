package com.example.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Booksentity;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Booksentity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Booksentity getBookById(Long id) { // Changed long → Long
        return bookRepository.findById(id).orElse(null);
    }

    public Booksentity addBook(String title, String author, String category, int stock, 
                               BigDecimal price, String description, MultipartFile image) throws IOException {
        Booksentity book = new Booksentity();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setStock(stock);
        book.setPrice(price);
        book.setDescription(description);

        if (image != null && !image.isEmpty()) {
            book.setImage(image.getBytes());
        }
        return bookRepository.save(book);
    }

    public Booksentity updateBookStock(Long bookId, int stock) { // Changed long → Long
        Booksentity book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setStock(stock);
            return bookRepository.save(book);
        }
        return null;
    }

    public void deleteBook(Long bookId) { // Changed long → Long
        bookRepository.deleteById(bookId);
    }

    public List<Booksentity> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.searchBooks(query);
    }
}