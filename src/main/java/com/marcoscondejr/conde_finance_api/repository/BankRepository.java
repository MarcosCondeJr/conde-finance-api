package com.marcoscondejr.conde_finance_api.repository;

import com.marcoscondejr.conde_finance_api.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {
    Boolean existsByCode(String code);
}