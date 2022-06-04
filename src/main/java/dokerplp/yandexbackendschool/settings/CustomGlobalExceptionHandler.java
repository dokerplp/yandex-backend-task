package dokerplp.yandexbackendschool.settings;

import dokerplp.yandexbackendschool.exception.ImportException;
import dokerplp.yandexbackendschool.model.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ImportException.class)
    public ResponseEntity<Error> customImportExceptionHandler(HttpStatus status, Exception e) {
        return new ResponseEntity<>(new Error(status), status);
    }

}
