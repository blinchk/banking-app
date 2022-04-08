package ee.laus.banking.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDirectionException extends RuntimeException {
    public InvalidDirectionException() {
        super("Direction must be \"IN\" or \"OUT\"");
    }
}
