package com.vn.hcmus.qlqtpm.backendvnexpress.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {
    public static final Logger logger = LogManager.getLogger(UnauthorizedException.class);
    private static final long serialVersionUID = 7664785096389091489L;
    private String errorMessage;

    public UnauthorizedException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public UnauthorizedException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public UnauthorizedException(String message, Throwable cause, String errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }

    public UnauthorizedException(Throwable cause, String errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
    }

}
