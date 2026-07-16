package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.bank.BankFilter;
import com.marcoscondejr.conde_finance_api.dto.bank.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankUpdateDTO;
import com.marcoscondejr.conde_finance_api.service.BankService;
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

@RestController
@RequestMapping("/api/bank")
@Tag(name = "Bank")
public class BankController {

    @Autowired
    private BankService service;

    @GetMapping
    @Operation(summary = "Listar bancos com paginação e filtros")
    @Parameters({
            @Parameter(name = "page", description = "Número da página", example = "0"),
            @Parameter(name = "size", description = "Quantidade de registros por página", example = "10"),
            @Parameter(name = "sort", description = "Ordenação no formato campo,direcao", example = "id,asc")
    })
    public ResponseEntity<Page<BankResponseDTO>> getBanks(
            @ParameterObject BankFilter filter,
            @Parameter(hidden = true)
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable
    ) {
        Page<BankResponseDTO> banks = this.service.getBanks(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(banks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponseDTO> getBankById(@PathVariable("id") String id) {
        BankResponseDTO bank = this.service.getBankById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(bank);
    }

    @PostMapping
    public ResponseEntity<BankResponseDTO> saveBank(@RequestBody @Valid BankRequestDTO data) {
        BankResponseDTO bank = this.service.saveBank(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(bank);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BankResponseDTO> updateBank(
            @PathVariable("id") String id, @RequestBody @Valid BankUpdateDTO data
    ) {
        BankResponseDTO bank = this.service.updateBank(Long.parseLong(id), data);
        return ResponseEntity.status(HttpStatus.OK).body(bank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBank(@PathVariable("id") String id) {
        this.service.deleteBank(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/options")
    @Operation(summary = "Listar todos os bancos sem filtro e paginação")
    public ResponseEntity<List<BankResponseDTO>> getBankOptions() {
        List<BankResponseDTO> banks = this.service.getBanksOptions();
        return ResponseEntity.status(HttpStatus.OK).body(banks);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<BankResponseDTO> updateStatus(
            @PathVariable String id,
            @PathVariable
            @Schema(
                    description = "Status do banco",
                    allowableValues = {"true", "false"},
                    example = "true"
            )
            String status
    ) {
        BankResponseDTO bank = service.updateStatus(Long.parseLong(id),
                Boolean.parseBoolean(status));
        return ResponseEntity.ok(bank);
    }
}
