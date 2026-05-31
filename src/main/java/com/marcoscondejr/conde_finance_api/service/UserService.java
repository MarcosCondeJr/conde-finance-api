package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.user.UserFilter;
import com.marcoscondejr.conde_finance_api.dto.user.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.user.UserResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.enums.UserRole;
import com.marcoscondejr.conde_finance_api.exception.ForbiddenException;
import com.marcoscondejr.conde_finance_api.exception.UnauthorizedAccessException;
import com.marcoscondejr.conde_finance_api.mapper.UserMapper;
import com.marcoscondejr.conde_finance_api.repository.UserRepository;
import com.marcoscondejr.conde_finance_api.specification.CategorySpecification;
import com.marcoscondejr.conde_finance_api.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    /**
     * Retorna a lista de usuários, somente para o usuário administrador
     *
     * @author      Marcos Conde
     * @since       30/05/2026
     * */
    public Page<UserResponseDTO> getAllUsers(UserFilter userFilter, Pageable pageable) {
        User user = this.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedAccessException("Usuário não autenticado");
        }

        if (user.getRole() != UserRole.ADMIN) {
            throw new ForbiddenException("Acesso não autorizado");
        }

        Specification<User> spec = UserSpecification.withFilters(userFilter);

        Page<User> users = this.repository.findAll(spec, pageable);
        return users.map(userMapper::toDTO);
    }
}
