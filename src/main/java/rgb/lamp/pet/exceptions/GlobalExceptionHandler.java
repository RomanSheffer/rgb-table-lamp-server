package rgb.lamp.pet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rgb.lamp.pet.dto.CustomExceptionDto;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionDto> handleCustomException(CustomException e) {

        CustomExceptionDto errorResponse = new CustomExceptionDto(

                Instant.now(),
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionDto> handleInvalidArgumentException(MethodArgumentNotValidException e) {

        CustomExceptionDto errorResponse = new CustomExceptionDto(

                Instant.now(),
                HttpStatus.BAD_REQUEST.toString(),
                Optional.ofNullable(e.getBindingResult().getFieldError())
                        .map(FieldError::getDefaultMessage)
                        .orElse("ошибка валидации")
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);

    }


}
