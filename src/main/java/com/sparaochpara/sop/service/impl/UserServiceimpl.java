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

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).get();
        return mapToUserDto(user);
    }

    @Override
    public void updateClub(UserDto userDto) {
        User user = mapToUser(userDto);
        userRepository.save(user);
    }

    private User mapToUser(UserDto user) {
        User userDto = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();
        return userDto;
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
