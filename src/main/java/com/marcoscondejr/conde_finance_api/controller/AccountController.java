package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.account.AccountRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountUpdateDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAccounts() {
        List<AccountResponseDTO> accounts = this.service.getAccounts();
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
}
