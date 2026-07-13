package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.user.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.user.UserResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.enums.UserRole;
import com.marcoscondejr.conde_finance_api.exception.BusinessException;
import com.marcoscondejr.conde_finance_api.exception.UnauthorizedException;
import com.marcoscondejr.conde_finance_api.mapper.UserMapper;
import com.marcoscondejr.conde_finance_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    public Page<UserResponseDTO> getAll(Pageable pageable) {
        User user = this.getCurrentUser();

        if (user.isActive() && user.getRole() == UserRole.USER) {
            throw new UnauthorizedException("Não foi possivel listar os usuários. Usuário sem permissão");
        }

        Page<User> users = repository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    public String helloUser(String nome) {
        return "Bem vindo " + nome + "!";
    }

    public String createUser(UserRequestDTO user) {
        return "";
    }
}
