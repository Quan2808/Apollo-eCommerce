package com.apollo.converter.impl;

import com.apollo.converter.UserConverter;
import com.apollo.dto.UserDTO;
import com.apollo.dto.UserLoginDTO;
import com.apollo.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public UserDTO convertEntityToDTO(User user) {
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Override
    public List<UserLoginDTO> convertEntitiesToDTOs(List<User> element) {
        return null;
    }

    @Override
    public List<UserDTO> entitiesToDTOs(List<User> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO entityToDTO(User element) {
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

}
