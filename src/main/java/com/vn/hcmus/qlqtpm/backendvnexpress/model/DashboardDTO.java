package com.vn.hcmus.qlqtpm.backendvnexpress.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long categoryId;
    private String title;
    private String slug;
    private List<PostDTO> postDTOList;
}
