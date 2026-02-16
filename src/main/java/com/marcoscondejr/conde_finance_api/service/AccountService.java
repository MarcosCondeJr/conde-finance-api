package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.account.AccountRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.account.AccountUpdateDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.exception.AccountAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.BankAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.mapper.AccountMapper;
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

    @Autowired
    private AccountMapper accountMapper;

    /**
     * Lista as contas de um determinado usuário
     *
     * @return  List<AccountResponseDTO>
     */
    public List<AccountResponseDTO> getAccounts() {
        Long userId = this.getCurrentUserId();
        return accountMapper.toDTOList(repository.findAllByUserId(userId));
    }

    /**
     * Busca uma determinada conta por Id
     *
     * @param   id  Id da conta
     *
     * @return  AccountResponseDTO
     */
    public AccountResponseDTO getAccountById(Long id) {
        var account = this.repository.findById(id);

        if (account.isEmpty()) {
            throw new ObjectNotFoundException("Conta não encontrada");
        }

        return accountMapper.toDTO(account.get());
    }

    /**
     * Salva uma nova conta
     *
     * @param   data    Dados da nova conta
     *
     * @return  AccountResponseDTO
     */
    public AccountResponseDTO saveAccount(AccountRequestDTO data) {
        User user = this.getCurrentUser();

        if (this.repository.existsByBankIdAndUserId(data.bankId(), user.getId())) {
            throw new AccountAlreadyExistsException("Já existe uma conta cadastrada com esse banco");
        }

        Bank bank = this.bankRepository.findById(data.bankId())
                .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrado"));

        if (!bank.getActive()) {
            throw new ObjectNotFoundException("Banco inativo");
        }

        Account account = new Account();
        account.setDescription(data.description());
        account.setBank(bank);
        account.setInitialBalance(data.initialBalance());
        account.setBalance(data.initialBalance());
        account.setUser(user);

        Account accountSave = this.repository.save(account);

        return accountMapper.toDTO(accountSave);
    }

    /**
     * Atualiza uma determinada conta
     *
     * @param   id      Id da conta a ser editada
     * @param   data    Dados a serem atualizados
     *
     * @return  AccountResponseDTO
     */
    public AccountResponseDTO updateAccount(Long id, AccountUpdateDTO data) {
        Account account = this.repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada"));

        Long userId = this.getCurrentUserId();

        if (data.bankId() != null) {
            if (this.repository.existsByBankIdAndUserId(data.bankId(), userId)) {
                throw new AccountAlreadyExistsException("Já existe uma conta cadastrada com esse banco");
            }

            Bank bank = this.bankRepository.findById(data.bankId())
                    .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrado"));

            account.setBank(bank);
        }

        if (data.description() != null) {
            account.setDescription(data.description());
        }

        if (data.initialBalance() != null) {
            account.setInitialBalance(data.initialBalance());
        }

        this.repository.save(account);

        return accountMapper.toDTO(account);
    }

    /**
     * Exclui uma conta por id
     *
     * @param   id  id da conta a ser excluida
     */
    public void deleteAccount(Long id) {
        if (!this.repository.existsById(id)) {
            throw new ObjectNotFoundException("Conta não encontrado");
        };

        this.repository.deleteById(id);
    }
}
