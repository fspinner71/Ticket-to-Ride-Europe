import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class Track {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 20;
    private static BufferedImage tracks[];
    private int color;
    private int x, y;
    private float rotation;

    static {
        tracks = new BufferedImage[9];
        try {
            tracks[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedTrack.png")); 
            tracks[Game.ORANGE] = ImageIO.read(GamePanel.class.getResource("/Images/OrangeTrack.png"));
            tracks[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowTrack.png")); 
            tracks[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenTrack.png")); 
            tracks[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueTrack.png")); 
            tracks[Game.PINK] = ImageIO.read(GamePanel.class.getResource("/Images/PinkTrack.png")); 
            tracks[Game.WHITE] = ImageIO.read(GamePanel.class.getResource("/Images/WhiteTrack.png")); 
            tracks[Game.BLACK] = ImageIO.read(GamePanel.class.getResource("/Images/BlackTrack.png")); 
            tracks[Game.ANY] = ImageIO.read(GamePanel.class.getResource("/Images/GrayTrack.png")); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Track(int color, int x, int y, float rotation) 
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public void paint(Graphics2D g) 
    {
        AffineTransform backup = g.getTransform();
        AffineTransform a = AffineTransform.getRotateInstance(rotation, x, y);
        g.setTransform(a);
        g.drawImage(tracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);
        g.setTransform(backup);
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public void rotate(float rotation) {
        this.rotation += rotation;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getColor() {
        return color;
    }
    public float getRotation() {
        return rotation;
    }
}
