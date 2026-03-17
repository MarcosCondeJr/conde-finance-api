package com.marcoscondejr.conde_finance_api.specification;

import com.marcoscondejr.conde_finance_api.dto.category.CategoryFilter;
import com.marcoscondejr.conde_finance_api.entity.Category;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {
    public static Specification<Category> withFilters(Long userId, CategoryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("id"), userId));

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
                        )
                );
            }

            if (filter.categoryType() != null) {
                predicates.add(cb.equal(root.get("categoryType"), filter.categoryType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
