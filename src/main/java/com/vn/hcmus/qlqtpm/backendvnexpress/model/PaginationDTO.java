package com.vn.hcmus.qlqtpm.backendvnexpress.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T> {
    @ApiModelProperty(value = "List")
    private List<T> list;

    @ApiModelProperty(example = "100", value = "totalPage")
    private Long totalPage;

    @ApiModelProperty(example = "90", value = "currentPage")
    private Long currentPage;

    @ApiModelProperty(example = "10", value = "size")
    private Long size;

    @ApiModelProperty(example = "1991", value = "totalItem")
    private Long totalItem;

    @ApiModelProperty(example = "10", value = "totalItemPerPage")
    private Long totalItemPerPage;

    @ApiModelProperty(example = "true", value = "isPrevious")
    private boolean isPrevious;

    @ApiModelProperty(example = "false", value = "isNext")
    private boolean isNext;
}
