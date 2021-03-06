import java.awt.*;

public class Man {

    double x;
    double y;
    double mass;
    int k = 10;
    double speed;
    double length;
    Vector direction;
    Color c;
    private int runningX; // -1=НАЛЕВО БЕЖИМ, 0=СТОИМ НА МЕСТЕ, +1=НАПРАВО БЕЖИМ
    private int runningY; // -1=ВВЕРХ БЕЖИМ, 0=СТОИМ НА МЕСТЕ, +1=ВНИЗ БЕЖИМ


    public Man(){
        x = 100;
        y = 100;
        mass = 20;
        length = mass;
        speed = 0.15;
        c = Color.MAGENTA;

        this.runningX = 0;         // Изначально мы стоим на месте
        this.runningY = 0;         // Изначально мы стоим на месте
        direction = new Vector();
    }
    public void drawMan(Graphics g){
        g.setColor(this.c);
        length = mass;
        g.fillOval((int) (x - length/2),(int) (y - length/2), (int) (length), (int) (length));
    }

    public void update(long dt){
        x += direction.vx * dt * speed;
        y += direction.vy * dt * speed;
    }


    public boolean checkEat(Food food){
        double l = Math.sqrt((this.x - food.x)*(this.x - food.x) + (this.y - food.y)*(this.y - food.y));
        length = mass;
        if(l < (length/2)){
            return true;
        }else {
            return false;
        }
    }





/*
    public void startRunningLeft() {
        runningX = -1;
    }
    public void startRunningRight() {
        runningX = 1;
    }

    public void stopRunningLeft() {
        if (runningX == -1) {
            runningX = 0;
        }
    }
    public void stopRunningRight() {
        if (runningX == 1) {
            runningX = 0;
        }
    }
    public void startRunningUp() {
        runningY = -1;
    }
    public void startRunningDown() {
        runningY = 1;
    }

    public void stopRunningUp() {
        if (runningY == -1) {
            runningY = 0;
        }
    }
    public void stopRunningDown() {
        if (runningY == 1) {
            runningY = 0;
        }
    }

    public void update(long dt) {
        x += dt * speed * runningX;
        y += dt * speed * runningY;
    }
 */
}
