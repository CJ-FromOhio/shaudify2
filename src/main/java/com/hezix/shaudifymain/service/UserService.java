package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import com.hezix.shaudifymain.mapper.user.UserCreateMapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import com.hezix.shaudifymain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional()
    public ReadUserDto save(CreateUserDto createUserDto) {
        if(!createUserDto.getPassword().equals(createUserDto.getPasswordConfirm())){
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation not equals");
        }
        createUserDto.setPassword(bcryptPasswordEncoder.encode(createUserDto.getPassword()));
        User user = mapCreateToEntity(createUserDto);
        user.setCreatedAt(Instant.now());
        User created_user = userRepository.save(user);
        return mapUserToRead(created_user);
    }
    @Transactional(readOnly = true)
    public ReadUserDto findUserById(Long id) {
        return mapUserToRead(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public ReadUserDto findUserByUsername(String username) {
        return mapUserToRead(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found")));
    }
    @Transactional(readOnly = true)
    public User findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User Entity with username " + username + " not found"));
    }
    @Transactional(readOnly = true)
    public UserDetails findUserDetailsByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(user.getRole())
                ))
                .orElseThrow(() -> new EntityNotFoundException("UserDetails with username " + username + " not found"));
    }
    @Transactional(readOnly = true)
    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Entity with id " + id + " not found"));
    }
    @Transactional(readOnly = true)
    public List<ReadUserDto> findAllUsers() {
        return mapListUserToListRead(userRepository.findAll());
    }
    @Transactional()
    public ReadUserDto deleteUserById(Long id) {
        var user = findUserById(id);
        userRepository.delete(mapReadToUser(user));
        return user;
    }

    //mappers
    public ReadUserDto mapUserToRead(User user) {
        return userReadMapper.toDto(user);
    }
    public CreateUserDto mapUserToCreate(User user) {
        return userCreateMapper.toDto(user);
    }
    public User mapReadToUser(ReadUserDto user) {
        return userReadMapper.toEntity(user);
    }
    public User mapCreateToEntity(CreateUserDto createUserDto) {
        return userCreateMapper.toEntity(createUserDto);
    }
    public List<ReadUserDto> mapListUserToListRead(List<User> userList) {
        return userReadMapper.toDtoList(userList);
    }
    public List<User> mapListReadToListUser(List<ReadUserDto> userList) {
        return userReadMapper.toEntityList(userList);
    }


}
