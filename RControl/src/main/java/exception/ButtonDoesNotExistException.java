package exception;

public class ButtonDoesNotExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Provided button does not exist";
    }
}
