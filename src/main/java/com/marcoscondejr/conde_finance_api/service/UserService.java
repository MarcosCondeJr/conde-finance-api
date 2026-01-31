package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.user.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public String helloUser(String nome) {
        return "Bem vindo " + nome + "!";
    }

    public String createUser(UserRequestDTO user) {
        return "";
    }
}
