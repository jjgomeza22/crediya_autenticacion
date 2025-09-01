package co.com.crediya.usecase.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String MESSAGE = "Email [%s] already exists";

    public DuplicateEmailException(String email) {
        super(MESSAGE.formatted(email));
    }
}
