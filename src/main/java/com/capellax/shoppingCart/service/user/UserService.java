package com.capellax.shoppingCart.service.user;

import com.capellax.shoppingCart.dto.UserDTO;
import com.capellax.shoppingCart.exceptions.AlreadyExistsException;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.User;
import com.capellax.shoppingCart.repository.UserRepository;
import com.capellax.shoppingCart.request.CreateUserRequest;
import com.capellax.shoppingCart.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(
            Long userId
    ) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
    }

    @Override
    public User createUser(
            CreateUserRequest request
    ) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("Oop! " + request.getEmail() + " already exists."));
    }

    @Override
    public User updateUser(
            UpdateUserRequest request,
            Long userId
    ) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(
            Long userId
    ) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("User not found");
                        });
    }

    @Override
    public UserDTO convertUserToUserDTO(
            User user
    ) {
        return modelMapper.map(user, UserDTO.class);
    }
}
