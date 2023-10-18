package com.vn.hcmus.qlqtpm.backendvnexpress.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
@Data
@NoArgsConstructor
public class ForbiddenException extends RuntimeException {
    public static final Logger logger = LogManager.getLogger(ForbiddenException.class);
    private static final long serialVersionUID = 7664785096389091489L;
    private String errorMessage;

    public ForbiddenException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));

    }

    public ForbiddenException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));

    }

    public ForbiddenException(String message, Throwable cause, String errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));

    }

    public ForbiddenException(Throwable cause, String errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
        logger.info(MessageFormat.format("BadRequestException message: {0}", errorMessage));

    }
}
