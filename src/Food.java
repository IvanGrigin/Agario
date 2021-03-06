import java.awt.*;

public class Food {
    int x;
    int y;
    Color c;
    double mass;

    public Food(int W, int H){
        x = (int) (Math.random() * W);
        y = (int) (Math.random() * H);
        mass = (Math.random()-0.25)*1;
        c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }
    public void drawFood(Graphics g){
        g.setColor(c);
        int n = 2;
        g.fillRect(x - n, y - n,2 * n,2 * n);
    }

}
