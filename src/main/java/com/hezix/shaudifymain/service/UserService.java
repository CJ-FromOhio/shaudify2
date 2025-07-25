package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import com.hezix.shaudifymain.filter.QPredicates;
import com.hezix.shaudifymain.filter.UserFilter;
import com.hezix.shaudifymain.mapper.user.UserCreateMapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import com.hezix.shaudifymain.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.hezix.shaudifymain.entity.user.QUser.user;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final MinioImageService minioImageService;

    @Transactional()
    public ReadUserDto save(CreateUserDto createUserDto) {
        if (!createUserDto.getPassword().equals(createUserDto.getPasswordConfirm())) {
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation not equals");
        }
        createUserDto.setPassword(bcryptPasswordEncoder.encode(createUserDto.getPassword()));
        User user = userCreateMapper.toEntity(createUserDto);
        user.setCreatedAt(Instant.now());
        User created_user = userRepository.save(user);
        return userReadMapper.toDto(created_user);
    }
    @Cacheable(
            value = "users:id",
            key = "#id"
    )
    @Transactional(readOnly = true)
    public ReadUserDto findUserById(Long id) {
        return userReadMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }

    @Cacheable(
            value = "users:username",
            key = "#username"
    )
    @Transactional(readOnly = true)
    public ReadUserDto findUserByUsername(String username) {
        return userReadMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found")));
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
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

    @Cacheable(
            value = "users:all",
            key = "#userFilter.hashCode() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    @Transactional(readOnly = true)
    public Page<ReadUserDto> findAllUsersByFilter(UserFilter userFilter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(userFilter.getFirstName(), user.firstName::containsIgnoreCase)
                .add(userFilter.getLastName(), user.lastName::containsIgnoreCase)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ReadUserDto> findAllUsers() {
        return userReadMapper.toDtoList(userRepository.findAll());
    }

    @Transactional()
    public ReadUserDto deleteUserById(Long id) {
        var user = findUserById(id);
        userRepository.delete(userReadMapper.toEntity(user));
        return user;
    }
    @Transactional()
    @Caching(evict = {
            @CacheEvict(value = "users:username", key="#user.username"),
            @CacheEvict(value = "users:id", key="#user.id"),
    })
    public ReadUserDto deleteUser(ReadUserDto user) {
        userRepository.delete(userReadMapper.toEntity(user));
        return user;
    }

    @Transactional()
    public ReadUserDto uploadImage(Long id, MultipartFile files) {
        User user = findUserEntityById(id);
        if (files == null || files.isEmpty()) {
            user.setImage("default_user_image.jpg");
            userRepository.save(user);
            return userReadMapper.toDto(user);
        }
        String fileName = minioImageService.upload(files);
        user.setImage(fileName);
        userRepository.save(user);
        return userReadMapper.toDto(user);
    }
}
