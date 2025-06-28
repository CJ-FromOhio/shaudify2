package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.filter.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        FilterUserRepository {
    Optional<User> findByUsername(String username);
}
