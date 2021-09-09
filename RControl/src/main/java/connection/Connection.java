package connection;

import com.fazecast.jSerialComm.SerialPort;
import emulator.HammerSetPosition;
import emulator.HammersPosition;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Connection {
    private OutputStream outputStream;
    private SerialPort port;

    public Connection(String portDescriptor) {
        this.port = SerialPort.getCommPort(portDescriptor);
        this.outputStream = this.port.getOutputStream();
    }

    public boolean close() {
        return this.port.closePort();
    }

    private void sendString(String message) throws IOException {
        this.outputStream.write(message.getBytes(StandardCharsets.UTF_8));
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
            sendString("arms 0");
        }
        else if(position == HammerSetPosition.HIGH) {
            sendString("arms 1");
        }
    }

    public void moveHammers(HammersPosition position) throws IOException {
        if(position == HammersPosition.LOW) {
            sendString("hammers 0");
        }
        else if(position == HammersPosition.HIGH) {
            sendString("hammers 1");
        }
    }
}
