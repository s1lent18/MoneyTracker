package com.example.MoneyTracker.models.repository;

import com.example.MoneyTracker.models.model.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Integer> {
}
