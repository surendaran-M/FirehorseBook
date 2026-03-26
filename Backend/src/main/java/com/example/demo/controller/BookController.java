package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Booksentity;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired
    private BookService bookService;

    // ✅ Get all books
    @GetMapping("/all")
    public List<Booksentity> getAllBooks() {
        return bookService.getAllBooks();
    }

    // ✅ Get book by ID
    @GetMapping("/{id}")
    public Booksentity getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // ✅ Add new book
    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("category") String category,
            @RequestParam("stock") int stock,
            @RequestParam("price") BigDecimal price, // Fixed type
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        try {
            Booksentity book = bookService.addBook(
                    title, author, category, stock, price, description, image
            );
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding book: " + e.getMessage());
        }
    }

    // ✅ Update book stock
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(
            @PathVariable Long id, // Fixed type
            @RequestParam int stock
    ) {
        try {
            Booksentity book = bookService.updateBookStock(id, stock);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Search books
    @GetMapping("/search")
    public List<Booksentity> searchBooks(
            @RequestParam(required = false) String q
    ) {
        return bookService.searchBooks(q);
    }

    // ✅ Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) { // Fixed type
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
