package com.vn.hcmus.qlqtpm.backendvnexpress.controller;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.UserDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.AddUserPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.LoginPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.UpdateUserPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.response.APIResponseSuccess;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    public static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<APIResponseSuccess<List<UserDTO>>> getListUser() {
        return ResponseEntity.ok(new APIResponseSuccess<>(userService.getAllUser()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<UserDTO>> getUserById(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(userService.getUserById(id)));
    }

    @PostMapping(value = " ")
    public ResponseEntity<APIResponseSuccess<String>> createUser(@RequestBody AddUserPayload payload) {
        return ResponseEntity.ok(new APIResponseSuccess<>(userService.createUser(payload)));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> updateUser(@RequestBody UpdateUserPayload payload, Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(userService.updateUser(payload, id)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> deleteUser(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(userService.deleteUser(id)));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<APIResponseSuccess<String>> loginByEmail(@RequestBody LoginPayload payload, HttpServletRequest request) {
        String loggedInUser = userService.logInByEmail(payload,request);
        
        // Tạo session và lưu thông tin đăng nhập của người dùng
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", loggedInUser);      
        return ResponseEntity.ok(new APIResponseSuccess<>(loggedInUser));
    }
}
