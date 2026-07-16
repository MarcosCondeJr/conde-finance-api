package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.account.AccountFilter;
import com.marcoscondejr.conde_finance_api.dto.account.AccountRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountUpdateDTO;
import com.marcoscondejr.conde_finance_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("/api/account")
@Tag(name = "Account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    @Parameters({
            @Parameter(name = "page", description = "Número da página", example = "0"),
            @Parameter(name = "size", description = "Quantidade de registros por página", example = "10"),
            @Parameter(name = "sort", description = "Ordenação no formato campo,direcao", example = "id,asc")
    })
    public ResponseEntity<Page<AccountResponseDTO>> getAccounts(
            @ParameterObject AccountFilter filter,
            @Parameter(hidden = true)
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "Id") Pageable pageable
    ) {
        Page<AccountResponseDTO> accounts = this.service.getAccounts(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("id") String id) {
        AccountResponseDTO account = this.service.getAccountById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> saveAccount(@RequestBody @Valid AccountRequestDTO data) {
        AccountResponseDTO account = this.service.saveAccount(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable("id") String id, @RequestBody @Valid AccountUpdateDTO data
    ) {
        AccountResponseDTO account = this.service.updateAccount(Long.parseLong(id), data);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") String id) {
        this.service.deleteAccount(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/options")
    @Operation(summary = "Listar todos as contas ativas e sem filtro e paginação")
    public ResponseEntity<List<AccountResponseDTO>> getBankOptions() {
        List<AccountResponseDTO> accounts = this.service.getAccountOptions();
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<AccountResponseDTO> updateStatus(
            @PathVariable String id,
            @PathVariable
            @Schema(
                    description = "Status do banco",
                    allowableValues = {"true", "false"},
                    example = "true"
            )
            String status
    ) {
        AccountResponseDTO account = service.updateStatus(
                Long.parseLong(id),
                Boolean.parseBoolean(status));
        return ResponseEntity.ok(account);
    }
}
