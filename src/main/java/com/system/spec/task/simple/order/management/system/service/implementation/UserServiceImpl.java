package com.system.spec.task.simple.order.management.system.service.implementation;

import com.system.spec.task.simple.order.management.system.domain.dto.CreateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UpdateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UserResponseDto;
import com.system.spec.task.simple.order.management.system.domain.exception.ResourceNotFoundException;
import com.system.spec.task.simple.order.management.system.domain.exception.ValidationException;
import com.system.spec.task.simple.order.management.system.domain.mappers.UserMapper;
import com.system.spec.task.simple.order.management.system.domain.models.UserEntity;
import com.system.spec.task.simple.order.management.system.domain.repository.UserRepository;
import com.system.spec.task.simple.order.management.system.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;



    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(CreateUserRequest userDTO) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new ValidationException("Email already in use: " + userDTO.getEmail());
        }
        var user = userMapper.mapToEntity(userDTO);
        var savedUser = userRepository.save(user);
        return userMapper.mapToResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        var  existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserEntity updatedUser =  UserEntity.builder()
                .name(updateUserRequest.getName() != null ? updateUserRequest.getName() : existingUser.getName())
                .build();

        var savedUser = userRepository.save(updatedUser);
        return userMapper.mapToResponse(savedUser);
    }


}