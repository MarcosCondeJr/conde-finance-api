package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.bank.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankUpdateDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.exception.BankAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    public List<Bank> getBanks() {
        return this.repository.findAll();
    }

    public Bank getBankById(Long id) {
        Optional<Bank> bank = this.repository.findById(id);

        if (bank.isEmpty()) {
            throw new ObjectNotFoundException("Banco com id " + id + " não encontrado");
        }

        return bank.get();
    }

    public Bank saveBank(BankRequestDTO data) {
        if (this.repository.existsByCode(data.code())) {
            throw new BankAlreadyExistsException("Já existe um banco com esse código");
        }

        Bank bank = new Bank();
        bank.setCode(data.code());
        bank.setName(data.name());

        return this.repository.save(bank);
    }

    public Bank updateBank(Long id, BankUpdateDTO data) {
        Bank bank = this.getBankById(id);

        if (data.code() != null) {
            bank.setCode(data.code());
        }

        if (data.name() != null) {
            bank.setName(data.name());
        }

        return this.repository.save(bank);
    }

    public void deleteBank(Long id) {
        if (!this.repository.existsById(id)) {
            throw new ObjectNotFoundException("Banco com id " + id + " não encontrado");
        }

        this.repository.deleteById(id);
    }
}
