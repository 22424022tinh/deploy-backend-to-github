package com.vn.hcmus.qlqtpm.backendvnexpress.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class APIResponseSuccess<T> {
    boolean success ;
    String message;
    int code;
    T data;

    public APIResponseSuccess(T data) {
        this.data = data;
        this.code = 200 ;
        this.success = true;
        this.message ="success";
    }
}
