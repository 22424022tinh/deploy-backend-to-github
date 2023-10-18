package com.vn.hcmus.qlqtpm.backendvnexpress.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiErrorResponse {
    private static final long serialVersionUID = 7664785096389091489L;

    @JsonProperty("statusCode")
    private int status;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private String path;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("trace")
    private String trace;

    @JsonProperty("metaData")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String metaData;
}
