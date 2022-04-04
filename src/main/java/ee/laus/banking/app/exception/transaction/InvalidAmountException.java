package ee.laus.banking.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("Amount cannot be negative number");
    }
}
