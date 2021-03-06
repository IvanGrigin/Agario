import java.awt.*;
import java.awt.event.MouseEvent;

public class Target {
    int x;
    int y;
    int w;
    int h;
    int centreX;
    int centreY;

    public Target(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        centreX = (int) (x + (w/2));
        centreX = (int) (y + (h/2));
        System.out.println(x+" "+y+ " "+ w+ " "+ h + " " + centreX+ " "+ centreY);
    }

    public void drawTarget(Graphics g){
        int n = 10;
        g.setColor(Color.RED);
        g.fillOval(x + w/2 - n, y + h/2 - n, 2 * n, 2 * n);

        g.setColor(Color.BLACK);
        g.drawOval(x,y,w,h);


    }



    public Vector whichSpeed(MouseEvent e){

        Vector v = new Vector();
        double length = Math.sqrt((e.getX()- x - w/2)*(e.getX()- x - w/2) + (e.getY()- y - h/2) * (e.getY()- y - h/2));
        if ((length < h/2)&&(length != 0)) {
            v.vx = (e.getX() - x - w/2) / length;
            v.vy = (e.getY() - y - h/2) / length;
        }
        System.out.println(v.vx +" "+v.vy);
        return v;
    }
}
