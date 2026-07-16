package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.specification.CategorySpecification;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryFilter;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryUpadateDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.exception.CategoryAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.mapper.CategoryMapper;
import com.marcoscondejr.conde_finance_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends BaseService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * Lista todas as categorias de um determinado usuário
     *
     * @return  CategoryResponseDTO
     */
    public Page<CategoryResponseDTO> getCategories(CategoryFilter categoryFilter, Pageable pageable) {
        Long userId = this.getCurrentUserId();

        Specification<Category> spec = CategorySpecification.withFilters(userId, categoryFilter);

        Page<Category> categories = repository.findAll(spec, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Lista todas as categorias de um determinado usuário
     *
     * @param   id    Id da categoria
     *
     * @return  CategoryResponseDTO
     */
    public CategoryResponseDTO getCategoryById(Long id) {
        var category = this.repository.findById(id);

        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Categoria não encontrada");
        }

        return categoryMapper.toDTO(category.get());
    }

    /**
     * Salva uma nova categoria
     *
     * @param   data    Dados para salvar a categoria
     *
     * @return  CategoryResponseDTO
     */
    public CategoryResponseDTO saveCategory(CategoryRequestDTO data) {
        User user = this.getCurrentUser();

        if (this.repository.existsByNameAndUserId(data.name(), user.getId())) {
            throw new CategoryAlreadyExistsException("Já existe uma categoria com esse nome");
        }

        Category category = new Category();
        category.setName(data.name());
        category.setUser(user);
        category.setCategoryType(data.categoryType());

        Category categorySave = this.repository.save(category);
        return categoryMapper.toDTO(categorySave);
    }

    /**
     * Atualiza uma categoria
     *
     * @param   id      Id da categoria a ser atualizada
     * @param   data    Dados atualizados para a cateogoria
     *
     * @return  CategoryResponseDTO
     */
    public CategoryResponseDTO updateCategory(Long id, CategoryUpadateDTO data) {
        Category category = this.repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        if (data.name() != null) {
            category.setName(data.name());
        }

        if (data.categoryType() != null) {
            category.setCategoryType(data.categoryType());
        }

        this.repository.save(category);

        return categoryMapper.toDTO(category);
    }

    /**
     * Deleta uma categoria
     *
     * @param   id  Id da categoria a ser deletada
     */
    public void deleteCategory(Long id) {
        if (!this.repository.existsById(id)) {
           throw new ObjectNotFoundException("Categoria não encontrada");
        };

        this.repository.deleteById(id);
    }

    public List<CategoryResponseDTO> getCategoryOptions() {
        Long userId = this.getCurrentUserId();

        return categoryMapper.toDTOList(repository.findAllByUserId(userId));
    }
}
