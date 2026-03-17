package org.example.backend.controller;

import org.example.backend.dto.UserDTO;
import org.example.backend.dto.UserSummaryDTO;
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

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return new ResponseEntity<>(new APIResponse<>(200, "User Updated", null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new APIResponse<>(200, "User Deleted", null), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", userService.getUserById(id)), HttpStatus.OK);
    }
    @GetMapping("/summary/{userId}")
    public ResponseEntity<APIResponse<UserSummaryDTO>> getUserSummary(@PathVariable Long userId) {
        return new ResponseEntity<>(new APIResponse<>(200, "User summary fetched", userService.getUserSummary(userId)), HttpStatus.OK);
    }
}