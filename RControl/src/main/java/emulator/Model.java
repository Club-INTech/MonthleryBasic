package emulator;

import java.util.List;

public class Model {

    private final float EPS = 0.3f;

    private float translationSpeed;
    private float rotationSpeed;

    private ModelState state;
    private HammerSetPosition hammerSetPosition;
    private HammersPosition hammersPosition;

    public Model() {
        this.state = ModelState.NONE;
        this.translationSpeed = 0.0f;
        this.rotationSpeed = 0.0f;
        hammerSetPosition = HammerSetPosition.LOW;
        hammersPosition = HammersPosition.LOW;
    }

    public void update(float x, float y, List<Boolean> buttonsState) {
        switch (this.state) {
            case NONE -> {
                if(Math.abs(x) >= Math.abs(y)) {
                    if(Math.abs(x) >= EPS) {
                        this.state = ModelState.MOVEMENT;
                        this.translationSpeed =  600 * (x / Math.abs(x));
                    }
                }
                else if(Math.abs(x) < Math.abs(y)) {
                    this.state = ModelState.ROTATION;
                    this.rotationSpeed = (3.14f / 2.0f) * (y / Math.abs(y));

                }
                else if(buttonsState.get(0)) {
                    this.state = ModelState.MOVE_HAMMER_SET;
                    this.hammerSetPosition = this.hammerSetPosition == HammerSetPosition.LOW ?  HammerSetPosition.HIGH : HammerSetPosition.LOW;
                }
                else if(buttonsState.get(3)) {
                    this.state = ModelState.MOVE_HAMMERS;
                    this.hammersPosition = this.hammersPosition == HammersPosition.LOW ?  HammersPosition.HIGH : HammersPosition.LOW;
                }
                break;
            }
            case MOVEMENT -> {
                if(Math.abs(x) >= EPS) {
                    break;
                }
                this.state = ModelState.NONE;
                break;
            }
            case ROTATION -> {
                if(Math.abs(y) >= EPS) {
                    break;
                }
                this.state = ModelState.NONE;
                break;
            }
            case MOVE_HAMMER_SET -> {
                if(buttonsState.get(0)) {
                    break;
                }
                this.state = ModelState.NONE;
            }
            case MOVE_HAMMERS -> {
                if(buttonsState.get(3)) {
                    break;
                }
                this.state = ModelState.NONE;
            }
        }

    }

    public ModelState getState() {
        return this.state;
    }

    public float getTranslationSpeed() {
        return this.translationSpeed;
    }

    public float getRotationSpeed() {
        return this.rotationSpeed;
    }

    public HammerSetPosition getHammerSetPosition() {
        return this.hammerSetPosition;
    }

    public HammersPosition getHammersPosition() {
        return this.hammersPosition;
    }
}
