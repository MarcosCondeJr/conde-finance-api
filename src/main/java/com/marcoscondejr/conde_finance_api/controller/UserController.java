package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public String helloUser() {
        return this.service.helloUser("");
    }

    @PostMapping
    public String createUser(@RequestBody UserRequestDTO user) {
        return this.service.createUser(user);
    }
}
