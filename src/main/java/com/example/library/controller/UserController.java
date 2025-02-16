package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id).orElseThrow();
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user,@PathVariable Long id){
        User updatedUser=userService.getUserById(id).orElseThrow();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setRentals(user.getRentals());
        return userService.saveUser(updatedUser);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


}
