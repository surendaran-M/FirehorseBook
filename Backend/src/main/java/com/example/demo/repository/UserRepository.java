package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Userentity;

@Repository
public interface UserRepository extends JpaRepository<Userentity, Long> { // Changed Integer → Long

    Optional<Userentity> findByEmail(String email);
}