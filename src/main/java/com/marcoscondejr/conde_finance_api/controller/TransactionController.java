package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionFilter;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionUpdateDTO;
import com.marcoscondejr.conde_finance_api.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
@Tag(name = "Transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactions(
            TransactionFilter filter, @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<TransactionResponseDTO> transactions = this.service.getTransactions(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> saveTransaction(
            @RequestBody @Valid TransactionRequestDTO data
    ) {
        TransactionResponseDTO transaction = this.service.saveTransaction(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable() String id, @RequestBody @Valid TransactionUpdateDTO data
    ) {
        TransactionResponseDTO transaction = this.service.updateTransaction(Long.parseLong(id), data);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") String id) {
        this.service.deleteTransaction(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
