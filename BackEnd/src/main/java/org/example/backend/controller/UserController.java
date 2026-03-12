package org.example.backend.controller;

import org.example.backend.dto.UserDTO;
import org.example.backend.service.UserService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "User Registered Successfully", null), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(new APIResponse<>(200, "Success", allUsers), HttpStatus.OK);
    }

    // මීට අමතරව Update සහ Delete කලින් විදියටම දාගන්න පුළුවන්
}