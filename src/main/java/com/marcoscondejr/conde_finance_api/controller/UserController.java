package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.user.UserFilter;
import com.marcoscondejr.conde_finance_api.dto.user.UserResponseDTO;
import com.marcoscondejr.conde_finance_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/users")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    @Operation(summary = "Listar todos os usuários do sistema")
    @Parameters({
            @Parameter(name = "page", description = "Número da página", example = "0"),
            @Parameter(name = "size", description = "Quantidade de registros por página", example = "10"),
            @Parameter(name = "sort", description = "Ordenação no formato campo,direcao", example = "id,asc")
    })
    public ResponseEntity<Page<UserResponseDTO>> getUsers(
            @ParameterObject UserFilter userFilter,
            @Parameter(hidden = true)
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<UserResponseDTO> users = this.service.getAllUsers(userFilter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
