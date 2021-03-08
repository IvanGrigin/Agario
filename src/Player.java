import java.awt.*;

public class Player {
    boolean respaun;
    double x;
    double y;
    double mass;
    double length;
    Color c;
    public Player(double x, double y, double m, Color c0){
        respaun = true;
        this.x = x;
        this.y = y;
        this.mass = m;
        this.c = c0;
    }
    public Player(){
        respaun = true;
        this.x = 0;
        this.y = 0;
        this.mass = 0;
        this.c = Color.BLACK;
    }

    public void drawPlayer(Graphics g){
        respaun = false;
        if((x == 400)&&(y == 400)){
            respaun = true;
        }
        length = mass;
        g.setColor(c);
        g.fillOval((int)(x - length/2),(int)(y - length/2),(int)(length),(int)(length));
        g.setColor(Color.WHITE);
        int m = (int)(this.mass);
        g.drawString(Integer.toString(m),(int)(x-3),(int)(y+3));

    }


}
