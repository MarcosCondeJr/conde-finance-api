package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByNameAndUserId(String name, Long userId);
}
