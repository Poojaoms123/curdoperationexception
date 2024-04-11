package com.example.project.Service;

import com.example.project.Exception.CollegeNotFound;
import com.example.project.Model.SaveRequest.SaveUserRequest;
import com.example.project.Model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Object saveOrUpdate(SaveUserRequest saveUserRequest);



    Object deleteById(Long userId) ;

    Object getById(Long userId);

    Object changeStatus(Long userId);

    List<User> getAllUser(String userName, String mobileNo, String email, Pageable pageable);


    Object getAllUser11(String userName, String userMobileNo, String userEmail, Pageable pageable);

    Object deleteMultipleUser(String userId);

    Object checkUserEmailExits(String userEmail, Long userId);

    Object checkMobileNoExits(String userMobileNo, Long userId);

    List<User> getAll();
}
