import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server extends MessageListener {
    ArrayList<StreamWorker> postmans = new ArrayList();
    ArrayList<String> textsOfServer = new ArrayList();

    public Server() {
    }

    public void run() throws IOException {
        ServerSocket server = new ServerSocket(5051);

        while(true) {
            Socket client = server.accept();
            StreamWorker postman = new StreamWorker(client.getInputStream(), client.getOutputStream());
            postman.addListener(this);
            postman.sendMessage("Your_Number " + (postmans.size()));
            for(int i = 0; i < this.textsOfServer.size(); i = i + 1) {
                postman.sendMessage(this.textsOfServer.get(i));
            }

            postman.start();
            this.postmans.add(postman);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }

    public void onMessage(String text) {
        textsOfServer.add(text);
        for (int i = 0; i < postmans.size(); i = i + 1){
            postmans.get(i).sendMessage(text);
        }
    }

    public void onDisconnect() {
        System.out.println("Клиент разорвал соединение");
    }
}
