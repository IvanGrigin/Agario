import java.awt.*;

public class Food {
    double x;
    double y;
    Color c;
    double mass;

    public Food(int W, int H){
        x = (int) (Math.random() * W);
        y = (int) (Math.random() * H);
        mass = (Math.random()-0.15)*3;
        c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }
    public Food(){
        x = 0;
        y = 0;
        mass = 0;
        c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }
    public void drawFood(Graphics g){
        g.setColor(c);
        int n = 2;
        g.fillRect((int)(x - n),(int) (y - n),2 * n,2 * n);
    }

}
