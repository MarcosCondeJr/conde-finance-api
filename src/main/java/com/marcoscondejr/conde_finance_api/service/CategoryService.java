package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.category.CategoryRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.exception.CategoryAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends BaseService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getCategories() {
        return this.repository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = this.repository.findById(id);

        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Categoria de id " + id + " não encontrado");
        }

        return category.get();
    }

    public CategoryResponseDTO saveCategory(CategoryRequestDTO data) {
        Long userId = this.getCurrentUserId();

        if (this.repository.existsByNameAndUserId(data.name(), userId)) {
            throw new CategoryAlreadyExistsException("Já existe uma categoria com esse nome");
        }

        Category category = new Category();
        category.setName(data.name());
        category.setUserId(userId);
        category.setCategoryType(data.categoryType());

        Category categorySave = this.repository.save(category);
        return CategoryResponseDTO.fromEntity(categorySave);
    }

    public void deleteCategory(Long id) {
        this.getCategoryById(id);

        this.repository.deleteById(id);
    }
}
