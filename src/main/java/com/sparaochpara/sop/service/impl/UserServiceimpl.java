package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.UserDto;
import com.sparaochpara.sop.model.User;
import com.sparaochpara.sop.repository.UserRepository;
import com.sparaochpara.sop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements UserService {

    private UserRepository userRepository;

    public UserServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User>users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }
    private UserDto mapToUserDto (User user ){
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();
        return userDto;

    }
}
