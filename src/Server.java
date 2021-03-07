import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server extends MessageListener {
    ArrayList<StreamWorker> postmans = new ArrayList();
    ArrayList<String> textsOfServer = new ArrayList();
    ArrayList<Food> foodOfServer = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    public Server() {
        for(int i = 0; i < 80; i = i + 1){
            foodOfServer.add(new Food(500, 500));
        }
    }

    public void run() throws IOException {
        ServerSocket server = new ServerSocket(5051);

        while(true) {
            Socket client = server.accept();
            StreamWorker postman = new StreamWorker(client.getInputStream(), client.getOutputStream());
            postman.addListener(this);
            postman.sendMessage("Your_Number " + (postmans.size()));
            for(int i = 0; i < this.textsOfServer.size(); i = i + 1) {
               // postman.sendMessage(this.textsOfServer.get(i));
            }
            for (int i = 0; i < foodOfServer.size(); i = i + 1){
                postman.sendMessage("RemoveFood " + i + " " + foodOfServer.get(i).x + " " + foodOfServer.get(i).y + " " + foodOfServer.get(i).mass);
            }
            for (int i = 0; i < players.size(); i = i + 1){
                postman.sendMessage("Player " + i + " " + players.get(i).x + " " + players.get(i).y + " " + players.get(i).mass);
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


        StringTokenizer tokenizer = new StringTokenizer(text);
        String token = tokenizer.nextToken();

        if(token.equals("Player")){
            String tokenNumber = tokenizer.nextToken();
            String tokenX = tokenizer.nextToken();
            String tokenY = tokenizer.nextToken();
            String tokenM = tokenizer.nextToken();


            int kolOfPlayer = Integer.parseInt(tokenNumber);
            while (kolOfPlayer >= players.size() - 1){
                players.add(new Player());
            }

            players.get(kolOfPlayer).x = Double.parseDouble(tokenX);
            players.get(kolOfPlayer).y = Double.parseDouble(tokenY);
            players.get(kolOfPlayer).mass = Double.parseDouble(tokenM);
        }
        if(token.equals("RemoveFood")){
            String tokenNumber = tokenizer.nextToken();
            int kolOfFood = Integer.parseInt(tokenNumber);

            String tokenX = tokenizer.nextToken();
            String tokenY = tokenizer.nextToken();
            String tokenM = tokenizer.nextToken();

            while (kolOfFood >= foodOfServer.size() - 1) {
                foodOfServer.add(new Food());
            }
            Food f = new Food();
            f.x =  Double.parseDouble(tokenX);
            f.y =  Double.parseDouble(tokenY);
            f.mass =  Double.parseDouble(tokenM);
            foodOfServer.set(kolOfFood, f);
        }


        for (int i = 0; i < postmans.size(); i = i + 1){
            postmans.get(i).sendMessage(text);
        }
        System.out.println(text);
    }

    public void onDisconnect() {
        System.out.println("Клиент разорвал соединение");
    }
}
