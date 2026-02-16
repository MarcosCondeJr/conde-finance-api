package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.repository.AccountRepository;
import com.marcoscondejr.conde_finance_api.repository.CategoryRepository;
import com.marcoscondejr.conde_finance_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService extends BaseService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Lista as transações de um determinado usuário
     *
     * @return  List<TransactionResponseDTO>
     */
    public List<TransactionResponseDTO> getTransactions() {
        Long userId = this.getCurrentUserId();
        return this.repository.findAllTransactionsByUser(userId);
    }

    /**
     * Salva uma nova Transação
     *
     * @param   data    Dados para salvar a transação
     *
     * @return  TransactionResponseDTO
     */
    public TransactionResponseDTO saveTransaction(TransactionRequestDTO data) {
        Account account = this.accountRepository.findById(data.accountId())
                .orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada"));

        Category category = this.categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setTransactionDate(data.transactionDate());
        transaction.setTransactionType(data.transactionType());
        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setPaymentMethod(data.paymentMethod());

        Transaction savedTransaction = this.repository.save(transaction);

        return TransactionResponseDTO.fromEntity(savedTransaction);
    }
}
