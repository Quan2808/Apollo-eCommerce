package com.apollo.converter;

import com.apollo.dto.UserDTO;
import com.apollo.dto.UserLoginDTO;
import com.apollo.entity.User;

import java.util.List;

public interface UserConverter {
    UserDTO convertEntityToDTO(User user);
    List<UserLoginDTO> convertEntitiesToDTOs(List<User> users);
    List<UserDTO> entitiesToDTOs(List<User> element);
    UserDTO entityToDTO(User element);

}
