package com.ecom.controller;

import com.ecom.entity.User;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/v1")
public class UserController {
     private  final UserService userService;
     public UserController(UserService userService){
         this.userService=userService;
     }
    @PostMapping("/save")
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
         return  userService.getUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?>  updateUser(@PathVariable Long id,@RequestBody User user){
         return ResponseEntity.ok(userService.updateUser(id,user));
    }
}
