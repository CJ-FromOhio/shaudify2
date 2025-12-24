package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.querydsl.core.types.Predicate;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
         QuerydslPredicateExecutor<User> {
    @Timed(value = "user.repository.findByUsername")
    Optional<User> findByUsername(String username);
    @Timed(value = "user.repository.findByEmail")
    Optional<User> findByEmail(String email);
}
