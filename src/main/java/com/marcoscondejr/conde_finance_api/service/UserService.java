package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String helloUser(String nome) {
        return "Bem vindo " + nome + "!";
    }

    public String createUser(UserRequestDTO user) {
        return "";
    }
}
