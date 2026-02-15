package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        select new com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO(
            t.id,
            new com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO(
                a.id,
                a.bank,
                a.description,
                a.initialBalance,
                a.balance,
                a.active
            ),
            new com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO(
                c.id,
                c.name,
                c.categoryType
            ),
            t.transactionDate,
            t.description,
            t.transactionType,
            t.paymentMethod,
            t.amount
        )
        from Transaction t
        join t.account a
        join t.category c
        where t.account.user.id = :userId
    """)
    List<TransactionResponseDTO> findAllTransactionsByUser(Long userId);
}
