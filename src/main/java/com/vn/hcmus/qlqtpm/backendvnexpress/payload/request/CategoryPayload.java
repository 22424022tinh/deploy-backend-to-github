package com.vn.hcmus.qlqtpm.backendvnexpress.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CategoryPayload {
    private String title;
    private String slug;
    private Long userId;
}
