package com.marcoscondejr.conde_finance_api.Specification;

import com.marcoscondejr.conde_finance_api.dto.bank.BankFilter;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BankSpecification {

    public static Specification<Bank> withFilters (BankFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.code() != null && !filter.code().isBlank()) {
                predicates.add(
                        cb.like(
                            cb.lower(root.get("code")),
                            "%" + filter.code().toLowerCase() + "%"
                ));
            }

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
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
