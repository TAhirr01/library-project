package com.example.library.service;

import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        List<User> userList= userRepository.findAll();
        return UserMapper.toUserDTOList(userList);
    }

    public UserDTO getUserById(Long id){
        User user= userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("İstifadəçi tapılmadı"));
        return UserMapper.toUserDTO(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        User updatedUser= userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Istifadeci tapılmadı, ID:"+id));
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());;
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
