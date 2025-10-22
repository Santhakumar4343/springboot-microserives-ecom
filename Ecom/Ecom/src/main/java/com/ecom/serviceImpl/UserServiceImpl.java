package com.ecom.serviceImpl;

import com.ecom.entity.User;
import com.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    List<User> users=new ArrayList<>();
   private Long nextId=1L;
    @Override
    public String addUser(User user) {
        user.setId(nextId++);
         users.add(user);
         return "user add successfully";
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUser(Long id) {
       return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public boolean updateUser(Long id, User user) {
        return users.stream().filter(user1-> user1.getId().equals(id))
                .findFirst().map(user1 -> {
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            return true;
        }).orElse(false);

    }

}
