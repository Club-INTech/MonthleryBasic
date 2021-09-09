import controllers.Joystick;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class InteractiveJoystickTest {

    private Joystick joystick = null;

    @Test
    public void StartJoystick() {
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
        while(true) {
            try {
                joystick.update();
                float x = joystick.getX();
                float y = joystick.getY();
                boolean b1 = joystick.getButtonsState().get(0);
                boolean b2 = joystick.getButtonsState().get(3);
                if(Math.abs(x) >= 0.5) {
                    System.out.printf("x: %f\n", x);
                }
                if(Math.abs(y) >= 0.5) {
                    System.out.printf("y: %f\n", y);
                }
                if(b1) {
                    System.out.println("green button triggered");
                }
                if(b2) {
                    System.out.println("yellow button triggered");
                }

                Thread.sleep(20);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
