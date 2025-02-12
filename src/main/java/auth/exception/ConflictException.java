package auth.exception;

public class ConflictException extends RuntimeException {

    public static final String BAD_CREDENTIALS = "Неверные имя пользователя или пароль";
    public static final String ACCESS_DENIED = "Вы не имеете доступа в эту систему";

    public ConflictException(String message) {
        super(message);
    }
}
