package com.vn.hcmus.qlqtpm.backendvnexpress.service;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.UserDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.AddUserPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.LoginPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.UpdateUserPayload;

import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public interface UserService {
    List<UserDTO> getAllUser();
    UserDTO getUserById(Long id);
    String logInByEmail(LoginPayload payload, HttpServletRequest request);
    String createUser(AddUserPayload payload);
    String updateUser(UpdateUserPayload payload, Long id);
    String deleteUser(Long id);
}
