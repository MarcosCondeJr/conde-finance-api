package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.exception.BusinessException;
import com.marcoscondejr.conde_finance_api.specification.BankSpecification;
import com.marcoscondejr.conde_finance_api.dto.bank.BankFilter;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    @Autowired
    private BankMapper bankMapper;

    /**
     * Lista os bancos com paginação e
     * filtros
     *
     * @return  List<BankResponseDTO>
     */
    public Page<BankResponseDTO> getBanks(BankFilter filter, Pageable pageable) {
        Specification<Bank> spec = BankSpecification.withFilters(filter);

        Page<Bank> banks = repository.findAll(spec, pageable);
        return banks.map(bankMapper::toDTO);
    }

    /**
     * Lista todos os bancos para opções
     *
     * @return  List<BankResponseDTO>
     */
    public List<BankResponseDTO> getBanksOptions() {
        return bankMapper.toDTOList(repository.findAll());
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

    /**
     * Atualiza o status de um banco, ativando ou inativando
     *
     * @param   id      Id do banco
     * @param   active  tipo de status, true -> ativo, false -> inativo
     */
    public BankResponseDTO updateStatus(Long id, Boolean active) {
        Bank bank = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banco não encontrato."));

        if (bank.getActive().equals(active)) {
            throw new BusinessException(
                    "O Banco já está " + (active ? "ativo" : "inativo")
            );
        }

        bank.setActive(active);
        repository.save(bank);

        return bankMapper.toDTO(bank);
    }
}
