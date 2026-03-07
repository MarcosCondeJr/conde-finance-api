package com.marcoscondejr.conde_finance_api.Specification;

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

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
