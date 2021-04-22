package Server;

public class ChatSetverExceptions extends RuntimeException {

    public ChatSetverExceptions(String message) {
        super(message);
    }

    public ChatSetverExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
