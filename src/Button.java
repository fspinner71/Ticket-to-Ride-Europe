import java.awt.*;
import java.awt.image.*;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;

    public Button(int x, int y, int width, int height, BufferedImage image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    

    public void setPosition(int new_x, int new_y){
        this.x = new_x;
        this.y = new_y;
    }

    public void setSize(int new_width, int new_height){
        this.width = new_width;
        this.height = new_height;
    }

    public void setImage(BufferedImage new_img){
        this.image = new_img;
    }
    
    public int getX()
    {
    	return x;
    }
    
    public int getY()
    {
    	return y;
    }

    public int getWidth()
    {
    	return width;
    }
    public int getHeight()
    {
    	return height;
    }
    public void paint(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public boolean isInside(int mouse_x, int mouse_y){
        return (mouse_x >= this.x && mouse_x <= this.x + this.width && mouse_y >= y && mouse_y <= this.y + this.height) ? true : false;
    }
}