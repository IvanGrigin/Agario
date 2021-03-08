import java.awt.*;

public class Man {

    boolean isLive;
    boolean respaun;
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
        isLive = true;
        respaun = true;
        x = 400;
        y = 400;
        mass = 20;
        length = mass;
        speed = 0.15;
        c = Color.MAGENTA;

        this.runningX = 0;         // Изначально мы стоим на месте
        this.runningY = 0;         // Изначально мы стоим на месте
        direction = new Vector();
    }
    public void drawMan(Graphics g){
        respaun = false;
        if((x == 400)&&(y == 400)){
            respaun = true;
        }
        if (isLive) {
            g.setColor(this.c);
            length = mass;
            g.fillOval((int) (x - length / 2), (int) (y - length / 2), (int) (length), (int) (length));
            g.setColor(Color.WHITE);
            int m = (int)(this.mass);
            g.drawString(Integer.toString(m),(int)(x-3),(int)(y+3));
        }else{
            isLive = true;
            mass = 10;
            x = 400;
            y = 400;
        }
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

    public boolean checkPlayer(Player p){
        double l = Math.sqrt((this.x - p.x)*(this.x - p.x) + (this.y - p.y)*(this.y - p.y));
        length = mass;
        if(l < (length/2)){
            return true;
        }else{
            return false;
        }
    }
}
