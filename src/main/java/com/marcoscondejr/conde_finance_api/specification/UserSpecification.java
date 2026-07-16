package com.marcoscondejr.conde_finance_api.specification;

import com.marcoscondejr.conde_finance_api.dto.user.UserFilter;
import com.marcoscondejr.conde_finance_api.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> withFilters (UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.login() != null && !filter.login().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("login")),
                                "%" + filter.login().toLowerCase() + "%"
                        ));
            }

            if (filter.email() != null && !filter.email().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.email().toLowerCase() + "%"
                        )
                );
            }

            if (filter.username() != null) {
                predicates.add(
                        cb.equal(root.get("active"), filter.username())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
