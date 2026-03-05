package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.bank.BankRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.bank.BankUpdateDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import com.marcoscondejr.conde_finance_api.exception.BankAlreadyExistsException;
import com.marcoscondejr.conde_finance_api.exception.ObjectNotFoundException;
import com.marcoscondejr.conde_finance_api.mapper.BankMapper;
import com.marcoscondejr.conde_finance_api.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    @Autowired
    private BankMapper bankMapper;

    /**
     * Lista todos os bancos
     *
     * @return  List<BankResponseDTO>
     */
    public Page<BankResponseDTO> getBanks(Pageable pageable) {
        Page<Bank> banks = repository.findAll(pageable));
        return bankMapper.toDTOList(banks);
    }

    /**
     * Busca um banco pelo Id
     *
     * @param   id  Id do banco
     *
     * @return  BankResponseDTO
     */
    public BankResponseDTO getBankById(Long id) {
        var bank = this.repository.findById(id);

        if (bank.isEmpty()) {
            throw new ObjectNotFoundException("Banco não encontrado");
        }

        return bankMapper.toDTO(bank.get());
    }

    /**
     * Salva um novo banco
     *
     * @param   data    Dados do novo banco
     *
     * @return  BankResponseDTO
     */
    public BankResponseDTO saveBank(BankRequestDTO data) {
        if (this.repository.existsByCode(data.code())) {
            throw new BankAlreadyExistsException("Já existe um banco com esse código");
        }

        Bank bank = new Bank();
        bank.setCode(data.code());
        bank.setName(data.name());

        Bank savedBank = repository.save(bank);

        return bankMapper.toDTO(savedBank);
    }

    /**
     * Atualiza um registro existente do banco
     *
     * @param   id      Id do banco a ser atualizado
     * @param   data    Dados a serem atualizados
     *
     * @return  BankResponseDTO
     */
    public BankResponseDTO updateBank(Long id, BankUpdateDTO data) {
        Bank bank = this.repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrado"));

        if (data.code() != null) {
            bank.setCode(data.code());
        }

        if (data.name() != null) {
            bank.setName(data.name());
        }

        Bank updateBank = repository.save(bank);

        return bankMapper.toDTO(updateBank);
    }

    /**
     * Exclui um determinado registro do banco
     *
     * @param   id  Id do banco a ser excluido
     */
    public void deleteBank(Long id) {
        if (!this.repository.existsById(id)) {
            throw new ObjectNotFoundException("Banco não encontrado");
        }

        this.repository.deleteById(id);
    }
}
