package com.marcoscondejr.conde_finance_api.mapper;

import com.marcoscondejr.conde_finance_api.dto.transaction.TransactionResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

    TransactionResponseDTO toDTO(Transaction transaction);

    List<TransactionResponseDTO> toDTOList(List<Transaction> transactions);
}
