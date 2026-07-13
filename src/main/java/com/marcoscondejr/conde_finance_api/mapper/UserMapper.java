package com.marcoscondejr.conde_finance_api.mapper;

import com.marcoscondejr.conde_finance_api.dto.user.UserResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponseDTO toDTO (User user);

    List<UserResponseDTO> toDtoList(List<User> users);
}
