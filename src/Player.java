import java.awt.*;

public class Player {
    double x;
    double y;
    double mass;
    double length;
    Color c;
    public Player(double x, double y, double m, Color c0){
        this.x = x;
        this.y = y;
        this.mass = m;
        this.c = c0;
    }
    public Player(){
        this.x = 0;
        this.y = 0;
        this.mass = 0;
        this.c = Color.BLACK;
    }

    public void drawPlayer(Graphics g){
        length = mass;
        g.setColor(c);
        g.drawOval((int)(x - length/2),(int)(y - length/2),(int)(length),(int)(length));
    }


}
