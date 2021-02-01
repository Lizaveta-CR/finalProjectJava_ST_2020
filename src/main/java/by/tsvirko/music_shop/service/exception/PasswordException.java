package by.tsvirko.music_shop.service.exception;

public class PasswordException extends Exception {
    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(Throwable cause) {
        super(cause);
    }
}
