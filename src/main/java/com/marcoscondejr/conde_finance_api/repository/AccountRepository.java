package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("""
        select new com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO(
            a.id,
            a.bank,
            a.description,
            a.initialBalance,
            a.balance,
            a.active
        )
        from Account a
        where a.user.id = :userId
    """)
    List<AccountResponseDTO> findAllAccountsByUserId(Long userId);
    Boolean existsByBankIdAndUserId(Long bankId, Long UserId);
}
