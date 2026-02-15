package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.category.CategoryRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryUpadateDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.User;
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

    /**
     * Lista todas as categorias de um determinado usuário
     *
     * @return  CategoryResponseDTO
     */
    public List<CategoryResponseDTO> getCategories() {
        Long userId = this.getCurrentUserId();
        return this.repository.findAllByUserId(userId);
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
            throw new ObjectNotFoundException("Categoria de id " + id + " não encontrado");
        }

        return CategoryResponseDTO.fromEntity(category.get());
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
        return CategoryResponseDTO.fromEntity(categorySave);
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
        Optional<Category> category = this.repository.findById(id);

        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Categoria de id " + id + " não encontrado");
        }

        Category categoryUpdate = category.get();

        if (data.name() != null) {
            categoryUpdate.setName(data.name());
        }

        if (data.categoryType() != null) {
            categoryUpdate.setCategoryType(data.categoryType());
        }

        this.repository.save(categoryUpdate);

        return CategoryResponseDTO.fromEntity(categoryUpdate);
    }

    /**
     * Deleta uma categoria
     *
     * @param   id  Id da categoria a ser deletada
     */
    public void deleteCategory(Long id) {
        this.getCategoryById(id);

        this.repository.deleteById(id);
    }
}
