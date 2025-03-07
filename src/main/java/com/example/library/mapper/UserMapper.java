package com.example.library.mapper;

import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users){
        if (users==null) return null;
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user:users){
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setFullName(user.getName());
            dto.setEmail(user.getEmail());
            userDTOS.add(dto);
        }
        return userDTOS;
    }
}
