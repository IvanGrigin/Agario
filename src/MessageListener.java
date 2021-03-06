
import java.io.IOException;

public abstract class MessageListener {
    public MessageListener() {
    }

    public abstract void onMessage(String var1) throws IOException;

    public abstract void onDisconnect();

    public void onException(Exception e) {
        e.printStackTrace();
    }
}
