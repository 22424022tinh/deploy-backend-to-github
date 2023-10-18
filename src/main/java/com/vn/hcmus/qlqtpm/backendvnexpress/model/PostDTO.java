package com.vn.hcmus.qlqtpm.backendvnexpress.model;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PostDTO {

    private String title;

    private String slug;

    private String summary;

    private String content;

    private Long categoryId;

    private Integer view;

    private Date createdAt;

    private Long createdBy;
}
