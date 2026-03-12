package org.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// මෙතනින් කියන්නේ මේ error එක ආවොත් Spring වලදී BAD_REQUEST (400) status එක යවන්න කියලයි
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}