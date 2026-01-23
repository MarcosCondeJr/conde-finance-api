package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.AuthenticationDTO;
import com.marcoscondejr.conde_finance_api.dto.LoginResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.UserRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.UserResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.exception.UserAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.infra.security.TokenService;
import com.marcoscondejr.conde_finance_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            User user = (User) auth.getPrincipal();
            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDTO(UserResponseDTO.fromEntity(user), token));
        } catch (Exception e) {
            System.out.println("Esse é oerror: "+ e.getClass().getName());
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRequestDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            throw new UserAlreadyExistsException();
        }

        String hashPassword = this.passwordEncoder.encode(data.password());

        User newUser = new User(data.name(), data.email(), data.login(), hashPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
