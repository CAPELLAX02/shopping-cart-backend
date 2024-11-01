package com.capellax.shoppingCart.controller;

import com.capellax.shoppingCart.dto.UserDTO;
import com.capellax.shoppingCart.exceptions.AlreadyExistsException;
import com.capellax.shoppingCart.exceptions.ResourceNotFoundException;
import com.capellax.shoppingCart.model.User;
import com.capellax.shoppingCart.request.CreateUserRequest;
import com.capellax.shoppingCart.request.UpdateUserRequest;
import com.capellax.shoppingCart.response.ApiResponse;
import com.capellax.shoppingCart.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // IUserService ???

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(
            @PathVariable Long userId
    ) {
        try {
            User user = userService.getUserById(userId);
            UserDTO userDTO = userService.convertUserToUserDTO(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {
        try {
            User user = userService.createUser(request);
            UserDTO userDTO = userService.convertUserToUserDTO(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success", userDTO));

        } catch (AlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("User already exists", null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            @PathVariable Long userId
    ) {
        try {
            User user = userService.updateUser(request, userId);
            UserDTO userDTO = userService.convertUserToUserDTO(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success", userDTO));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found", null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable Long userId
    ) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success", null));

        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found", null));
        }
    }

}
