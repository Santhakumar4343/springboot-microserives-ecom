package com.ecom.dto;

import com.ecom.entity.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private AddressRequest address;
}
