package com.vn.hcmus.qlqtpm.backendvnexpress.payload.request;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PostPayload {
    private String title;

    private String slug;

    private String summary;

    private String content;

    private Long categoryId;

    private Long userId;
}
