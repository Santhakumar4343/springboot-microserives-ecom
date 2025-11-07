package com.ecom.serviceImpl;

import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;
import com.ecom.entity.Address;
import com.ecom.entity.User;
import com.ecom.mapper.UserMapper;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user= UserMapper.userRequest(userRequest);
        User response=  userRepository.save(user);
        return UserMapper.userResponse(response);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::userResponse).toList();
    }

    @Override
    public UserResponse getUser(Long id) {
        return UserMapper.userResponse(userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found with"+id)));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        // Fetch the user from DB
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update basic details
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());

        // Update role if provided
        if (userRequest.getRole() != null) {
            existingUser.setRole(userRequest.getRole());
        }

        // Update address if provided
        if (userRequest.getAddress() != null) {
            Address address = existingUser.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            existingUser.setAddress(address);
        }

        // Save updated entity
        User updatedUser = userRepository.save(existingUser);

        // Convert entity to response DTO
        return UserMapper.userResponse(updatedUser);
    }


}
