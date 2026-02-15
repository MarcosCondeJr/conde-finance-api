package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            select new com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO(
                c.id,
                c.name,
                c.categoryType
            )
            from Category c
            where c.user.id = :userId
    """)
    List<CategoryResponseDTO> findAllByUserId(Long userId);
    Boolean existsByNameAndUserId(String name, Long userId);
}
