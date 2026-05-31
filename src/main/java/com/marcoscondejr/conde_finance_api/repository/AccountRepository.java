package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Page<Account> findAllByUserId(Pageable pageable, Long userId);
    Boolean existsByBankIdAndUserId(Long bankId, Long UserId);
    Boolean existsByBankIdAndUserIdAndIdNot(Long bankId, Long userId, Long id);
    List<Account> findAllByActiveAndUserId(boolean active, Long userId);
}
