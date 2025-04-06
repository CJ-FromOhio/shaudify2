package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.User;
import com.hezix.shaudifymain.entity.dto.user.CreateUserDto;
import com.hezix.shaudifymain.entity.dto.user.ReadUserDto;
import com.hezix.shaudifymain.mapper.user.UserCreateMapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import com.hezix.shaudifymain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;

    @Transactional()
    public ReadUserDto save(CreateUserDto createUserDto) {
        User user = mapCreateToEntity(createUserDto);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return mapUserToRead(user);
    }
    @Transactional(readOnly = true)
    public ReadUserDto findUserById(Long id) {
        return mapUserToRead(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public List<ReadUserDto> findAllUsers() {
        return userReadMapper.toDtoList(userRepository.findAll());
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
    public User mapReadToUser(ReadUserDto user) {
        return userReadMapper.toEntity(user);
    }
    public User mapCreateToEntity(CreateUserDto createUserDto) {
        return userCreateMapper.toEntity(createUserDto);
    }


}
