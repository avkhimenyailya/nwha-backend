package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.UserDTO;
import io.grayproject.nwha.api.dto.authentication.ChangePasswordDTO;

import java.security.Principal;

public interface UserService {

    UserDTO getUser(Principal principal);

    UserDTO changeUsername(Principal principal, String username);

    void changePassword(Principal principal, ChangePasswordDTO changePasswordDTO);
}
