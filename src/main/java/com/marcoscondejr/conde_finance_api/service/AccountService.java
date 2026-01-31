package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AccountService extends BaseService {

    @Autowired
    private AccountRepository repository;

    private List<Account> getAccounts() {
        return this.repository.findAll();
    }

    private Account getAccountById(Long id) {
        Optional<Account> account = this.repository.findById(id);

        if (account.isEmpty()) {
            throw new ObjectNotFoundException("Conta com id " + id + " não encontrado");
        }

        return account.get();
    }

//    private Account saveAccount()

//    private Account updateAccount()

//    private void deleteAccount()
}
