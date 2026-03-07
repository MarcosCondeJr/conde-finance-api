package com.marcoscondejr.conde_finance_api.Specification;

import com.marcoscondejr.conde_finance_api.dto.bank.BankFilter;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BankSpecification {

    public static Specification<Bank> withFilter (BankFilter filter)
    {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCode() != null && !filter.getCode().isBlank()) {
                predicates.add(cb.equal(root.get("code"), filter.getCode()));
            }

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.getName().toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
