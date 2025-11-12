package com.common.dto;

import com.common.entity.Role;
import lombok.Data;

@Data
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private AddressResponse address;

}
