package com.sparaochpara.sop.service;
import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.User;

import java.util.List;




public interface UserService {
    List<UserDto>findAllUsers();
    User saveUser(UserDto userDto);

    UserDto findUserByEmail(String email);

    void updateClub(UserDto user);
}
