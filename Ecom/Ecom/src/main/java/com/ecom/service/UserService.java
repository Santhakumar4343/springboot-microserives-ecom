package com.ecom.service;
import com.ecom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public String addUser(User user);
    public List<User> getAllUsers();
    public Optional<User> getUser(Long id);
    public boolean updateUser(Long id,User user);
}

