package com.example.MoneyTracker.models.repository;

import com.example.MoneyTracker.models.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
}

