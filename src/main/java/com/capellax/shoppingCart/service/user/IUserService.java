package com.capellax.shoppingCart.service.user;

import com.capellax.shoppingCart.dto.UserDTO;
import com.capellax.shoppingCart.model.User;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request);

    void deleteUser(Long userId);

    UserDTO convertUserToUserDTO(User user);

}
