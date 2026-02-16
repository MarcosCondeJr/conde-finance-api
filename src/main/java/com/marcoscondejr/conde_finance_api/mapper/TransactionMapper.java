package com.marcoscondejr.conde_finance_api.mapper;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

    @Mapping(source = "account", target = "account")
    @Mapping(source = "category", target = "category")
    TransactionResponseDTO toDTO(Transaction transaction);
}
