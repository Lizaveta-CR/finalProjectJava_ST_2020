package by.tsvirko.music_shop.config.exception;

public class ApplicationConfigException extends Exception {
    public ApplicationConfigException() {
    }

    public ApplicationConfigException(String message) {
        super(message);
    }

    public ApplicationConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationConfigException(Throwable cause) {
        super(cause);
    }

    public ApplicationConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
