package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService service;

    @GetMapping
    public List<Bank> loadBanks() {
        return this.service.loadBanks();
    }

    @PostMapping()
    public ResponseEntity<?> saveBank(@RequestBody @Valid BankRequestDTO data) {
        Bank bank = this.service.saveBank(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(bank);
    }
}
