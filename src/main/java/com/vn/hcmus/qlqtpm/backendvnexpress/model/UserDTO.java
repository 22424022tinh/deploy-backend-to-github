package com.vn.hcmus.qlqtpm.backendvnexpress.model;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private ROLE role;
    private String active;
    private Date createdAt;
    private Date updatedAt;
}
