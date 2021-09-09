package controllers;


import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Joystick {

    private float x;
    private float y;

    private final Controller controller;

    private List<Boolean> buttonsState;


    public Joystick() {
        this.controller = null;
        buttonsState = new ArrayList<>();
    }

    public Joystick(Controller joystickController) {
        controller = joystickController;
        buttonsState = new ArrayList<>();
    }


    public boolean update() throws Exception {
        buttonsState.clear();
        assert controller != null;
        boolean isValid = controller.poll();
        if(!isValid) {
            return false;
        }
        Arrays.stream(controller.getComponents()).forEach(component -> {
            if (component.getName().equals("A") || component.getName().equals("B") || component.getName().equals("X") || component.getName().equals("Y")) {
                buttonsState.add(component.getPollData() == 1.0f ? Boolean.TRUE : Boolean.FALSE);
            }
        });
        return true;
    }

    public float getX() {
        assert controller != null;
        return -controller.getComponent(Component.Identifier.Axis.Y).getPollData();
    }
    public float getY() {
        assert controller != null;
        return controller.getComponent(Component.Identifier.Axis.RX).getPollData();
    }

    public List<Boolean> getButtonsState() {
        return buttonsState;
    }
}
