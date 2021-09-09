import emulator.Emulator;

public class Main {
    public static void main(String[] args) {
        Emulator emulator = new Emulator("Microsoft X-Box 360 pad", 20);
        emulator.emulate();
    }
}
