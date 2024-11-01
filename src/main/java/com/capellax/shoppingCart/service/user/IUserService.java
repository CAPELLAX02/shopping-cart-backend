package com.capellax.shoppingCart.service.user;

import com.capellax.shoppingCart.dto.UserDTO;
import com.capellax.shoppingCart.model.User;
import com.capellax.shoppingCart.request.CreateUserRequest;
import com.capellax.shoppingCart.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);

    UserDTO convertUserToUserDTO(User user);

}
