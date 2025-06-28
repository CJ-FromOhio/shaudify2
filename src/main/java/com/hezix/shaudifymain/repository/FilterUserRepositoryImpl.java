package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.user.QUser;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.filter.QPredicates;
import com.hezix.shaudifymain.filter.UserFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.hezix.shaudifymain.entity.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {
    private final EntityManager em;
    @Override
    public List<User> findAllByFilter(UserFilter userFilter) {
        var predicate = QPredicates.builder()
                .add(userFilter.firstName(), user.firstName::containsIgnoreCase)
                .add(userFilter.lastName(), user.lastName::containsIgnoreCase)
                .build();

        return new JPAQuery<User>(em)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
