package com.jadz.fund.adapter.rest.exception;

import com.jadz.fund.domain.exception.BadRequestException;
import com.jadz.fund.domain.exception.NotFoundException;
import com.jadz.fund.domain.exception.ResourceUpdateException;
import com.jadz.fund.funds.model.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler {

  private static final Map<Class<?>, HttpStatus> EXCEPTIONS_MAPPING = new HashMap<>();

  static {
    EXCEPTIONS_MAPPING.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
    EXCEPTIONS_MAPPING.put(NotFoundException.class, HttpStatus.NOT_FOUND);
    EXCEPTIONS_MAPPING.put(MethodArgumentTypeMismatchException.class, HttpStatus.BAD_REQUEST);
    EXCEPTIONS_MAPPING.put(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
    EXCEPTIONS_MAPPING.put(ResourceUpdateException.class, HttpStatus.FORBIDDEN);
  }

  private static HttpStatus resolveStatus(Exception exception) {
    return EXCEPTIONS_MAPPING
        .getOrDefault(exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<ApiError> handleException(Exception ex,
                                                    NativeWebRequest request) {
    return getProblemResponseEntity(ex, request);
  }

  private ResponseEntity<ApiError> getProblemResponseEntity(Exception cause, NativeWebRequest request) {
    final var status = resolveStatus(cause);
    final var apiError = new ApiError();
    apiError.setStatus(String.valueOf(status.value()));
    apiError.setMessage(cause.getMessage());
    apiError.setError(status.getReasonPhrase());
    apiError.setPath(request.getContextPath());
    apiError.setTimestamp(LocalDateTime.now().toString());
    return ResponseEntity.status(status)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body(apiError);
  }

}
