import com.fazecast.jSerialComm.SerialPort;
import connection.Connection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class InteractiveConnectionTest {

    @Test
    public void StartConnection() {
        Connection connection = new Connection("/dev/ttyACM0");
        try {
            connection.sendTranslation(500.0f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
