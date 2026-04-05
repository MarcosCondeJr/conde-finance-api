package com.marcoscondejr.conde_finance_api.specification;

import com.marcoscondejr.conde_finance_api.dto.account.AccountFilter;
import com.marcoscondejr.conde_finance_api.entity.Account;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> withFilters(Long userId, AccountFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("id"), userId));

            if (filter.bankId() != null) {
                predicates.add(cb.equal(root.get("bank").get("id"), filter.bankId()));
            }

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("description")),
                                "%" + filter.description().toLowerCase() + "%"
                        )
                );
            }

            if (filter.active() != null) {
                predicates.add(
                        cb.equal(root.get("active"), filter.active())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
