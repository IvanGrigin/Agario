
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Frame extends JFrame implements KeyEventDispatcher, MouseMotionListener, MouseListener {
    StreamWorker postman;
    int numberOfPlayer = 0;
    public Man man;
    public Target target;
    private long previousWorldUpdateTime; // Храним здесь момент времени когда физика мира обновлялась в последний раз
    ArrayList<Food> eat;
    ArrayList<Player> players;

    public Frame(){
        this.setSize(800, 800);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.setResizable(true);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        target = new Target(50,getHeight() - 100,80,80);
        man = new Man();
        this.previousWorldUpdateTime = System.currentTimeMillis();
        eat = new ArrayList<>();
        players = new ArrayList<>();
        firstFood();

        repaint();
    }

    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();
        }

        g = bufferStrategy.getDrawGraphics();


        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);


        if(eat != null) {
            for (int i = 0; i < eat.size(); i = i + 1) {
                eat.get(i).drawFood(g);
            }
        }

        if(players != null){
            for (int i = 0; i < players.size(); i = i + 1){
                players.get(i).drawPlayer(g);
            }
        }
        if((man != null) || (target != null)) {
            man.drawMan(g);
            target.drawTarget(g);
            updateWorldPhysics();
            sentState();
        }


        g.dispose();
        bufferStrategy.show();
    }

    public void setPostman(StreamWorker p) {
        this.postman = p;
    }

    public void sentState() {
        this.postman.sendMessage("Player " + numberOfPlayer + " " + man.x + " " + man.y + " " + man.mass);
    }
    public void sentNewFood(int i, Food testFood){
        this.postman.sendMessage("RemoveFood " + i + " " + testFood.x + " " + testFood.y + " " +testFood.mass + " " + testFood.c.toString() + " ");

    }

    public void sentFirstState() {
        this.postman.sendMessage("newPlayer " + numberOfPlayer + " " + man.x + " " + man.y + " " + man.mass + " "+ man.c.toString());

        String text = "";
        for (int i = 0; i < eat.size(); i = i + 1){
            Food testFood = eat.get(i);
            text = text + " "+ testFood.x + " " + testFood.y + " " +testFood.mass + " " + testFood.c.toString() + " ";
        }
        this.postman.sendMessage("Food "+ eat.size() + " " + text);
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        /*
        if (e.getID() == KeyEvent.KEY_PRESSED) { // Если кнопка была нажата (т.е. сейчас она зажата)
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                man.startRunningLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                man.startRunningRight();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                man.startRunningUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                man.startRunningDown();
            }
        }

        if (e.getID() == KeyEvent.KEY_RELEASED) {     // Если кнопка была отпущена - мы должны прекратить бег
            if (e.getKeyCode() == KeyEvent.VK_LEFT) { // но только бег в ту сторону, которой соответствует отпущенная кнопка
                man.stopRunningLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                man.stopRunningRight();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                man.stopRunningUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                man.stopRunningDown();
            }
        }*/
        repaint();

        return false;
    }

    void updateWorldPhysics() {
        long currentTime = System.currentTimeMillis();
        long dt = currentTime - previousWorldUpdateTime; // нашли сколько миллисекунд прошло с предыдущего обновления физики мира

        man.update(dt);

        previousWorldUpdateTime = currentTime;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        man.direction = target.whichSpeed(e);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        man.direction = target.whichSpeed(e);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        man.direction = target.whichSpeed(e);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        man.direction = target.whichSpeed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        man.direction = new Vector();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void firstFood(){
        for(int i = 0; i < 80; i = i + 1){
            eat.add(new Food(getWidth(), getHeight()));
        }
    }
    public void checkEatFood(){
        for (int i = 0; i < eat.size(); i = i + 1){
            if(man.checkEat(eat.get(i))){
                man.mass = man.mass + eat.get(i).mass;
                eat.remove(i);
                eat.add(new Food(getWidth(),getHeight()));
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Frame f = new Frame();
        while(true){
            f.updateWorldPhysics();
            f.repaint();
            f.checkEatFood();
            Thread.sleep(20);
        }
    }
}
