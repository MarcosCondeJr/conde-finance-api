package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.bank.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankUpdateDTO;
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

    @GetMapping("/{id}")
    public Bank getBankById(@PathVariable("id") String id) {
        return this.service.getBankById(Long.parseLong(id));
    }

    @PostMapping
    public ResponseEntity<?> saveBank(@RequestBody @Valid BankRequestDTO data) {
        Bank bank = this.service.saveBank(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(bank);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBank(
            @PathVariable("id") String id, @RequestBody @Valid BankUpdateDTO data
    ) {
        Bank bank = this.service.updateBank(Long.parseLong(id), data);
        return ResponseEntity.status(HttpStatus.OK).body(bank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBank(@PathVariable("id") String id) {
        this.service.deleteBank(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
