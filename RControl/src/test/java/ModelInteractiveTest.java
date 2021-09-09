import controllers.Joystick;
import emulator.Model;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ModelInteractiveTest {

    private Joystick joystick = null;
    private Model model = null;

    @Test
    public void StartModel() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Arrays.stream(controllers).forEach(controller -> {
            controller.poll();
            System.out.println(controller.getName());
            if(controller.getName().equals("Microsoft X-Box 360 pad") && joystick == null) {
                joystick = new Joystick(controller);
            }
        });
        if(joystick == null) {
            System.out.println("Joystick Microsoft X-Box 360 pad is not connected. Please connect your joystick for an interactive test");
        }

        model = new Model();

        while(true) {
            try {
                joystick.update();
                model.update(joystick.getX(), joystick.getY(), joystick.getButtonsState());
                System.out.println("state: " + model.getState() + "; send: " + model.mustSend());
                Thread.sleep(20);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
