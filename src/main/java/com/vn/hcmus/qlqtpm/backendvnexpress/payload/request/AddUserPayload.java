package com.vn.hcmus.qlqtpm.backendvnexpress.payload.request;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AddUserPayload {
    private String username;
    private String email;
    private String password;
    private ROLE role;
}
