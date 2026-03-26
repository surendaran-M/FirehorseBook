package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Ordersentity;

@Repository
public interface OrderRepository extends JpaRepository<Ordersentity, Long> {

    List<Ordersentity> findByUserId(String userId);
}