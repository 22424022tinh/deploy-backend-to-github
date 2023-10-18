package com.vn.hcmus.qlqtpm.backendvnexpress.service.impl;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.ROLE;
import com.vn.hcmus.qlqtpm.backendvnexpress.entity.UserEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.HttpClientErrorException;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.UserDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.AddUserPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.LoginPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.UpdateUserPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.UserRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOs = userEntities.stream().map((item) -> {
            UserDTO userDTO = UserDTO
                    .builder()
                    .userId(item.getUserId())
                    .username(item.getUsername())
                    .email(item.getEmail())
                    .role(item.getRole())
                    .active(String.valueOf(item.getActive()))
                    .createdAt(item.getCreatedAt())
                    .updatedAt(item.getUpdatedAt())
                    .build();
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public UserDTO getUserById(Long id) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "userId không tồn tại!");
            }
            UserDTO userDTO = UserDTO
                    .builder()
                    .userId(userEntity.get().getUserId())
                    .username(userEntity.get().getUsername())
                    .email(userEntity.get().getEmail())
                    .role(userEntity.get().getRole())
                    .active(String.valueOf(userEntity.get().getActive()))
                    .createdAt(userEntity.get().getCreatedAt())
                    .updatedAt(userEntity.get().getUpdatedAt())
                    .build();

            return userDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public String createUser(AddUserPayload payload) {
        UserEntity userEntity1 = userRepository.isExistEmail(payload.getEmail());
        if(userEntity1==null)
        {
        BCryptPasswordEncoder hash= new BCryptPasswordEncoder();
        String encrypt=hash.encode(payload.getPassword());
        
        try {
            UserEntity userEntity = UserEntity.builder()
                    .username(payload.getUsername())
                    .email(payload.getEmail())
                    .password(encrypt)
                    .active(true)
                    .role(ROLE.USER)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            userRepository.save(userEntity);
            return "Tạo tài khoản thành công";
        } catch (Exception e) {
            throw e;
        }
    }
    else
    { 
        return "Email đã được sử dụng";
    }
    }

    @Override
    public String updateUser(UpdateUserPayload payload, Long id) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "userId không tồn tại!");
            }
            userEntity.get().setEmail(payload.getEmail());
            userEntity.get().setRole(payload.getRole());
            userEntity.get().setActive(payload.getActive());
            userEntity.get().setUpdatedAt(new Date());

            userRepository.save(userEntity.get());

            return "Update success";
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteUser(Long id) {
        try {
            userRepository.deleteById(id);

            return "Delete success";
        } catch (Exception e) {
            throw e;
        }
    }
    public boolean decodePassword(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
    @Override
    public String logInByEmail(LoginPayload payload, HttpServletRequest request) {
    try {
        UserEntity userEntity = userRepository.logInByEmail(payload.getEmail());
        if (userEntity == null) {
            return "Email chưa được đăng ký";
        } else {
            boolean passwordCorrect = decodePassword(payload.getPassword(), userEntity.getPassword());
            if (passwordCorrect) {
                // Tạo session và lưu thông tin đăng nhập của người dùng
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedInUser", userEntity);
                return userEntity.getUsername()+userEntity.getRole()+"1";
            } else {
                return "Mật khẩu không chính xác";
            }
        }
    } catch (Exception e) {
        throw e;
    }
}
}
