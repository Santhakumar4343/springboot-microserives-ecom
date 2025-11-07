package com.ecom.service;

import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse addUser(UserRequest user);
    public List<UserResponse> getAllUsers();
    public UserResponse getUser(Long id);
    public UserResponse updateUser(Long id,UserRequest user);
}

