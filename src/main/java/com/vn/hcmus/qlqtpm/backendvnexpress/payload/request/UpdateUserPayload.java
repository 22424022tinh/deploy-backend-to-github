package com.vn.hcmus.qlqtpm.backendvnexpress.payload.request;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserPayload {
    private String email;
    private ROLE role;
    private Boolean active;
}
