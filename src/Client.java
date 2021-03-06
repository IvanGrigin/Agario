import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client extends MessageListener {
    Frame frame = new Frame();
    int numberOfClient = -1;

    public Client(){
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        new Thread(() -> {
            while(true){
                client.frame.updateWorldPhysics();
                client.frame.repaint();
                client.frame.checkEatFood();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        client.start();

    }

    public void start() throws IOException {
        String host = "127.0.0.1";
        int port = 5051;
        Socket socket = new Socket(host, port);

        StreamWorker postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream());
        postman.addListener(this);
        postman.start();
        frame.setPostman(postman);
    }

    public void onMessage(String text){
        System.out.println(text);
        StringTokenizer tokenizer = new StringTokenizer(text);
        String token = tokenizer.nextToken();

        if (token.equals("Your_Number")) {
            String number = tokenizer.nextToken();
            numberOfClient = Integer.parseInt(number);
            frame.numberOfPlayer = numberOfClient;
        }

        if(token.equals("Player")){
            String tokenNumber = tokenizer.nextToken();
            String tokenX = tokenizer.nextToken();
            String tokenY = tokenizer.nextToken();
            String tokenM = tokenizer.nextToken();


            int kolOfPlayer = Integer.parseInt(tokenNumber);
            while (kolOfPlayer >= frame.players.size() - 1){
                frame.players.add(new Player());
            }

            frame.players.get(kolOfPlayer).x = Double.parseDouble(tokenX);
            frame.players.get(kolOfPlayer).y = Double.parseDouble(tokenY);
            frame.players.get(kolOfPlayer).mass = Double.parseDouble(tokenM);

        }
    }

    public void onDisconnect() {
    }
}
