package emulator;

import connection.Connection;
import controllers.Joystick;
import exception.JoystickDoesNotExistException;
import net.java.games.input.*;

import java.util.Arrays;

public class Emulator {

    private Joystick joystick;
    private int emulationPeriod;
    private Model model;
    private Connection connection;


    public Emulator(String joystickName, int period) throws JoystickDoesNotExistException {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Arrays.stream(controllers).forEach(controller -> {
            controller.poll();
            System.out.println(controller.getName());
            if(controller.getName().equals(joystickName) && joystick == null) {
                this.joystick = new Joystick(controller);
            }
        });
        if (joystick == null) {
            throw new JoystickDoesNotExistException();
        }
        this.emulationPeriod = period;
    }

    public void initModel() {
        this.model = new Model();
    }

    public void initConnection() {
        this.connection = new Connection("COM0");
    }

    public void emulate() {
        while(true) {
            try {
                joystick.update();
                model.update(joystick.getX(), joystick.getY(), joystick.getButtonsState());
                if(!model.mustSend()) {
                    Thread.sleep(this.emulationPeriod);
                    continue;
                }
                connection.sendStop();
                switch (model.getState()) {
                    case MOVEMENT -> {
                        connection.sendTranslation(model.getTranslationSpeed());
                        break;
                    }
                    case ROTATION -> {
                        connection.sendRotation(model.getRotationSpeed());
                        break;
                    }
                    case MOVE_HAMMER_SET -> {
                        connection.moveHammerSet(model.getHammerSetPosition());
                        break;
                    }
                    case MOVE_HAMMERS -> {
                        connection.moveHammers(model.getHammersPosition());
                        break;
                    }
                }
                Thread.sleep(this.emulationPeriod);
            } catch (Exception ex) {
                System.out.println("Exception occurred: " + ex.getMessage());
                connection.close();
                break;
            }

        }
    }

}
