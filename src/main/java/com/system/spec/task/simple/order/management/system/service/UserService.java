package com.system.spec.task.simple.order.management.system.service;

import com.system.spec.task.simple.order.management.system.domain.dto.CreateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UpdateUserRequest;
import com.system.spec.task.simple.order.management.system.domain.dto.UserResponseDto;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(UUID id);

    UserResponseDto createUser(CreateUserRequest userDTO);

    UserResponseDto updateUser(UUID id, UpdateUserRequest updateUserRequest);
}
