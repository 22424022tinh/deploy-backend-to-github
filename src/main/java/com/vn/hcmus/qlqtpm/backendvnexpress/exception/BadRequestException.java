package com.vn.hcmus.qlqtpm.backendvnexpress.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
    public static final Logger logger = LogManager.getLogger(BadRequestException.class);
    private static final long serialVersionUID = 7664785096389091489L;
    private String errorMessage;

    public BadRequestException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));
    }

    public BadRequestException(String errorMessage, Throwable cause, String message) {
        super(message, cause);
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));

    }

    public BadRequestException(String errorMessage, String message) {
        super(message);
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));
    }
}
