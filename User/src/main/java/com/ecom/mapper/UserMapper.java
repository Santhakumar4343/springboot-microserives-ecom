package com.ecom.mapper;

import com.ecom.dto.AddressResponse;
import com.ecom.dto.UserRequest;
import com.ecom.dto.UserResponse;
import com.ecom.entity.Address;
import com.ecom.entity.User;

public class UserMapper {
  static  public UserResponse userResponse(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressResponse addressResponse=new AddressResponse();
        addressResponse.setStreet(user.getAddress().getStreet());
        addressResponse.setState(user.getAddress().getState());
        addressResponse.setCountry(user.getAddress().getCountry());
        addressResponse.setCity(user.getAddress().getCity());
        userResponse.setAddress(addressResponse);
        }
        return userResponse;
    }
    static  public User userRequest(UserRequest userRequest){
        User user =new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if(userRequest.getAddress()!=null){
            Address address=new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
        return user;
    }
}
