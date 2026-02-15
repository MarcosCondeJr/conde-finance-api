package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService extends BaseService {

    @Autowired
    private TransactionRepository repository;

    /**
     * Lista as transações de um determinado usuário
     *
     * @return  List<TransactionResponseDTO>
     */
    public List<TransactionResponseDTO> getTransactions() {
        Long userId = this.getCurrentUserId();
        return this.repository.findAllTransactionsByUser(userId);
    }
}
