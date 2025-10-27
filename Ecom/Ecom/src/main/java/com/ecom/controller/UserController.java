package com.ecom.controller;

import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
     private  final UserService userService;
    @PostMapping("/save")
    public ResponseEntity<?> addUser(@RequestBody UserRequest user){
        return ResponseEntity.ok(userService.addUser(user));
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
         return  ResponseEntity.ok(userService.getUser(id));
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?>  updateUser(@PathVariable Long id,@RequestBody UserRequest user){
         return ResponseEntity.ok(userService.updateUser(id,user));
    }
}
