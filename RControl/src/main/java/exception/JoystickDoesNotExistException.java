package exception;

public class JoystickDoesNotExistException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Joystick was not initialized";
    }
}
