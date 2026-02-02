package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.account.AccountRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountUpdateDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.exception.AccountAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.BankAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.AccountRepository;
import com.marcoscondejr.conde_finance_api.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService extends BaseService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private BankRepository bankRepository;

    public List<Account> getAccounts() {
        return this.repository.findAll();
    }

    public Account getAccountById(Long id) {
        Optional<Account> account = this.repository.findById(id);

        if (account.isEmpty()) {
            throw new ObjectNotFoundException("Conta com id " + id + " não encontrado");
        }

        return account.get();
    }

    public AccountResponseDTO saveAccount(AccountRequestDTO data) {
        Long userId = this.getCurrentUserId();

        if (this.repository.existsByBankIdAndUserId(data.bankId(), userId)) {
            throw new AccountAlreadyExistsException("Já existe uma conta cadastrada com esse banco");
        }

        Bank bank = this.bankRepository.findById(data.bankId())
                .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrado"));

        if (!bank.getActive()) {
            throw new ObjectNotFoundException("Banco inativo");
        }

        Account account = new Account();
        account.setDescription(data.description());
        account.setBankId(data.bankId());
        account.setInitialBalance(data.initialBalance());
        account.setUserId(userId);

        Account accountSave = this.repository.save(account);

        return AccountResponseDTO.fromEntity(accountSave);
    }

    public AccountResponseDTO updateAccount(Long id, AccountUpdateDTO data) {
        Account account = this.getAccountById(id);

        Long userId = this.getCurrentUserId();

        if (data.bankId() != null) {
            if (this.repository.existsByBankIdAndUserId(data.bankId(), userId)) {
                throw new AccountAlreadyExistsException("Já existe uma conta cadastrada com esse banco");
            }

            Bank bank = this.bankRepository.findById(data.bankId())
                    .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrado"));

            account.setBankId(data.bankId());
        }

        if (data.description() != null) {
            account.setDescription(data.description());
        }

        if (data.initialBalance() != null) {
            account.setInitialBalance(data.initialBalance());
        }

        this.repository.save(account);

        return AccountResponseDTO.fromEntity(account);
    }

    public void deleteAccount(Long id) {
        this.getAccountById(id);

        this.repository.deleteById(id);
    }
}
