package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.exception.BankAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    public List<Bank> loadBanks() {
        return this.repository.findAll();
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
}
