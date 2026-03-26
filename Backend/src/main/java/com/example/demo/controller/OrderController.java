package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Ordersentity;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public Ordersentity placeOrder(@PathVariable int userId) { // Changed to int
        return orderService.placeOrder(userId);
    }

    @GetMapping("/user/{userId}")
    public List<Ordersentity> getOrders(@PathVariable int userId) { // Changed to int
        return orderService.getOrdersByUser(userId);
    }
}