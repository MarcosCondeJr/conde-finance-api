package com.marcoscondejr.conde_finance_api.mapper;

import com.marcoscondejr.conde_finance_api.dto.bank.BankResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BankMapper {

    BankResponseDTO toDTO(Bank bank);
}
