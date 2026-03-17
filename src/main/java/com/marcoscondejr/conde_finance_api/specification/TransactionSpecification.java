package com.marcoscondejr.conde_finance_api.specification;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionFilter;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> withFilters(Long userId, TransactionFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("account").get("user").get("id"), userId));

            if (filter.categoryId() != null) {
                predicates.add(
                        cb.equal(root.get("category").get("id"), filter.categoryId())
                );
            }

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("description")),
                                "%" + filter.description().toLowerCase() + "%"
                        )
                );
            }

            if (filter.transactionType() != null) {
                predicates.add(
                        cb.equal(root.get("transactionType"), filter.transactionType())
                );
            }

            if (filter.paymentMethod() != null) {
                predicates.add(
                        cb.equal(root.get("paymentMethod"), filter.paymentMethod())
                );
            }

            if (filter.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("transactionDate"), filter.startDate())
                );
            }

            if (filter.endDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("transactionDate"), filter.endDate())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
