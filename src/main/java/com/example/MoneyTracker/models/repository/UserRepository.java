package com.example.MoneyTracker.models.repository;

import com.example.MoneyTracker.models.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(
            value = """
                    SELECT * FROM USERS
                    WHERE EMAIL = :email
                    """, nativeQuery = true
    )
    Optional<Users> findByEmail(String email);
}

