package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.filter.UserFilter;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter userFilter);
}
