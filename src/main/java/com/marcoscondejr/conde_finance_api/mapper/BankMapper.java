package com.marcoscondejr.conde_finance_api.mapper;

import com.marcoscondejr.conde_finance_api.dto.bank.BankResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankMapper {

    BankResponseDTO toDTO(Bank bank);

    List<BankResponseDTO> toDTOList(List<Bank> banks);
}
