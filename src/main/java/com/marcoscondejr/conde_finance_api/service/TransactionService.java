package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.Specification.TransactionSpecification;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionFilter;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionUpdateDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
    public Page<TransactionResponseDTO> getTransactions(TransactionFilter filter, Pageable pageable) {
        Long userId = this.getCurrentUserId();

        Specification<Transaction> spec = TransactionSpecification.withFilters(userId, filter);

        Page<Transaction> transactions = repository.findAll(spec, pageable);

        return transactions.map(transactionMapper::toDTO);
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
     * Atualiza uma determinada transação
     *
     * @param   id      Id da transação a ser atualizada
     * @param   data    Dados a ser atualizados
     *
     * @return  TransactionResponseDTO
     */
    public TransactionResponseDTO updateTransaction(Long id, TransactionUpdateDTO data) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Transação não encontrada"));

        BigDecimal oldAmount = transaction.getAmount();
        CategoryType oldType = transaction.getTransactionType();

        if (data.transactionDate() != null) transaction.setTransactionDate(data.transactionDate());
        if (data.description() != null) transaction.setDescription(data.description());
        if (data.paymentMethod() != null) transaction.setPaymentMethod(data.paymentMethod());
        if (data.transactionType() != null) transaction.setTransactionType(data.transactionType());
        if (data.amount() != null) transaction.setAmount(data.amount());

        if (data.categoryId() != null &&
                !Objects.equals(data.categoryId(), transaction.getCategory().getId()))
        {
            Category category = this.categoryRepository.findById(data.categoryId())
                    .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));

            transaction.setCategory(category);
        }

        if (transaction.getCategory() != null && transaction.getTransactionType() != null ) {
            if (transaction.getCategory().getCategoryType() != transaction.getTransactionType()) {
                throw new ObjectNotFoundException(
                        "O tipo de transação informado não é compativel com o tipo de categoria");
            }
        }

        BigDecimal newAmount = (transaction.getAmount() != null) ? transaction.getAmount() : BigDecimal.ZERO;
        CategoryType newType = transaction.getTransactionType();

        BigDecimal oldDelta = delta(oldType, oldAmount);
        BigDecimal newDelta = delta(newType, newAmount);

        BigDecimal differenceAmount = newDelta.subtract(oldDelta);
        BigDecimal balanceAfter = transaction.getAccount().getBalance().add(differenceAmount);

        if (balanceAfter.signum() < 0) {
            throw new BusinessException("Saldo insuficiente para atualizar a transação.");
        }
        transaction.getAccount().setBalance(balanceAfter);

        Transaction updateTransaction = repository.save(transaction);

        return transactionMapper.toDTO(updateTransaction);
    }

    /**
     * Calcula o impacto financeiro (delta) de uma transação no saldo da conta.
     *
     * @param   type      Tipo da transação (REVENUE ou EXPENSE)
     * @param   amount    Valor da transação
     *
     * @return  BigDecimal
     */
    private BigDecimal delta(CategoryType type, BigDecimal amount) {
        if (type == null) {
            return BigDecimal.ZERO;
        }

        return type == CategoryType.REVENUE ? amount : amount.negate();
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
