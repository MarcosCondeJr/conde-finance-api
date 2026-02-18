package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import com.marcoscondejr.conde_finance_api.exception.BusinessException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.mapper.TransactionMapper;
import com.marcoscondejr.conde_finance_api.repository.AccountRepository;
import com.marcoscondejr.conde_finance_api.repository.CategoryRepository;
import com.marcoscondejr.conde_finance_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService extends BaseService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * Lista as transações de um determinado usuário
     *
     * @return  List<TransactionResponseDTO>
     */
    public List<TransactionResponseDTO> getTransactions() {
        Long userId = this.getCurrentUserId();
        List<Transaction> transactions = repository.findByAccountUserId(userId);

        return  transactionMapper.toDTOList(transactions);
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

        if (data.transactionType() != category.getCategoryType()) {
            throw new ObjectNotFoundException(
                    "O tipo de transação informado não é compativel com o tipo de categoria");
        }

        if (data.transactionType() == CategoryType.EXPENSE &&
                account.hasInsufficientBalance(data.amount())) {
            throw new BusinessException("Saldo insuficiente para realizar a transação.");
        }

        BigDecimal newBalance = (data.transactionType() == CategoryType.EXPENSE) ?
                account.getBalance().subtract(data.amount()) :
                account.getBalance().add(data.amount());

        account.setBalance(newBalance);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setTransactionDate(data.transactionDate());
        transaction.setTransactionType(data.transactionType());
        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setPaymentMethod(data.paymentMethod());

        Transaction savedTransaction = this.repository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    /**
     * Exclui uma determinada transação
     *
     * @param   id  Id da transação
     */
    public void deleteTransaction(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Transação não encontrada"));

        Account account = transaction.getAccount();

        if (transaction.getTransactionType() == CategoryType.REVENUE) {

            BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Não é possivel excluir a transação, pois o saldo ficará negativo");
            }

            account.setBalance(newBalance);
        } else {
           account.setBalance(account.getBalance().add(transaction.getAmount()));
        }

        repository.delete(transaction);
    }
}
