package com.zags.gorodovoy.Services.EmployeeService;


import com.zags.gorodovoy.dto.UserDto;
import com.zags.gorodovoy.models.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}