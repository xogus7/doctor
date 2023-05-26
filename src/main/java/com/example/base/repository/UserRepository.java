package com.example.base.repository;


import com.example.base.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository를 확장함으로써, UserRepository는 User 엔티티에 대한 CRUD(Create, Read, Update, Delete) 기능을 제공합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}