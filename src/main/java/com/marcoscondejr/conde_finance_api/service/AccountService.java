package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.account.AccountRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService extends BaseService {

    @Autowired
    private AccountRepository repository;

    public List<Account> getAccounts() {
        return this.repository.findAll();
    }

    public AccountResponseDTO getAccountById(Long id) {
        Optional<Account> account = this.repository.findById(id);

        if (account.isEmpty()) {
            throw new ObjectNotFoundException("Conta com id " + id + " não encontrado");
        }

        return AccountResponseDTO.fromEntity(account.get());
    }

    public AccountResponseDTO saveAccount(AccountRequestDTO data) {
        Long userId = this.getCurrentUserId();

        Account account = new Account();
        account.setDescription(data.description());
        account.setBankId(data.bankId());
        account.setInitialBalance(data.initialBalance());
        account.setUserId(userId);

        return AccountResponseDTO.fromEntity(account);

    }

//    private Account updateAccount()

//    private void deleteAccount()
}
