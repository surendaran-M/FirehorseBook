package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many OrderItems belong to one Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Ordersentity order;

    // Many OrderItems refer to one Book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Booksentity book;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public OrderItem() {}

    public OrderItem(Long id, Ordersentity order, Booksentity book, String title,
                     BigDecimal price, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.book = book;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ordersentity getOrder() { return order; }
    public void setOrder(Ordersentity order) { this.order = order; }

    public Booksentity getBook() { return book; }
    public void setBook(Booksentity book) { this.book = book; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
