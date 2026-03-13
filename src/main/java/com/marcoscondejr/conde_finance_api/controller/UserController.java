package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.user.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public String helloUser() {
        return this.service.helloUser("");
    }

    @PostMapping
    public String createUser(@Valid @RequestBody UserRequestDTO user) {
        return this.service.createUser(user);
    }
}
