package com.ecom;

import com.ecom.entity.User;
import com.ecom.repository.UserRepository;
import com.ecom.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
     void testAddUser(){
        User user= new User();
        user.setFirstName("santha");
        user.setLastName("kumar");
        user.setEmail("santha@gmail.com");
        user.setPhoneNumber("123456");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        String  response=userServiceImpl.addUser(user);
//        assertEquals("user add successfully",response);
        verify(userRepository,times(1)).save(user);
    }
}
