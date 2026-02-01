package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByBankIdAndUserId(Long bankId, Long UserId);
}
