package connection;

import com.fazecast.jSerialComm.SerialPort;
import emulator.HammerSetPosition;
import emulator.HammersPosition;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_SCANNER;

public class Connection {
    private PrintStream printStream;
    private SerialPort port;

    public Connection(String portDescriptor) {
        this.port = SerialPort.getCommPort(portDescriptor);
        System.out.println(this.port.getSystemPortName());
        this.port.setBaudRate(115200);
        port.setNumStopBits(1);
        port.setParity(0);
        port.setNumDataBits(8);
        port.setComPortTimeouts(TIMEOUT_SCANNER, 0, 0);
        this.printStream = new PrintStream(this.port.getOutputStream());
    }

    public boolean close() {
        return this.port.closePort();
    }

    private void sendString(String message) throws IOException {
        this.printStream.println(message);
        this.printStream.flush();
    }

    public void sendTranslation(float speed) throws IOException {
        if(speed >= 0) {
            sendString("av");
        } else {
            sendString("rc");
        }
    }

    public void sendRotation(float speed) throws IOException {
        if (speed >=0) {
            sendString("td");
        } else {
            sendString("tg");
        }
    }

    public void moveHammerSet(HammerSetPosition position) throws IOException{
        if(position == HammerSetPosition.LOW) {
            sendString("lower_dxl");
        }
        else if(position == HammerSetPosition.HIGH) {
            sendString("raise_dxl");
        }
    }

    public void moveHammers(HammersPosition position) throws IOException {
        if(position == HammersPosition.LOW) {
            sendString("lower_hammers");
        }
        else if(position == HammersPosition.HIGH) {
            sendString("raise_hammers");
        }
    }

    public void sendStop() throws IOException {
        sendString("sstop");
    }
}
