import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class Track {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 20;
    private static BufferedImage tracks[];
    private static BufferedImage tunnelTracks[];
    private int color;
    private int x, y;
    private float rotation;
    private boolean tunnel;

    static {
        tracks = new BufferedImage[9];
        tunnelTracks = new BufferedImage[9];
        try {
            tracks[Game.RED] = ImageIO.read(Track.class.getResource("/Images/RedTrack.png")); 
            tracks[Game.ORANGE] = ImageIO.read(Track.class.getResource("/Images/OrangeTrack.png"));
            tracks[Game.YELLOW] = ImageIO.read(Track.class.getResource("/Images/YellowTrack.png")); 
            tracks[Game.GREEN] = ImageIO.read(Track.class.getResource("/Images/GreenTrack.png")); 
            tracks[Game.BLUE] = ImageIO.read(Track.class.getResource("/Images/BlueTrack.png")); 
            tracks[Game.PINK] = ImageIO.read(Track.class.getResource("/Images/PinkTrack.png")); 
            tracks[Game.WHITE] = ImageIO.read(Track.class.getResource("/Images/WhiteTrack.png")); 
            tracks[Game.BLACK] = ImageIO.read(Track.class.getResource("/Images/BlackTrack.png")); 
            tracks[Game.ANY] = ImageIO.read(Track.class.getResource("/Images/GrayTrack.png")); 

            //tunnel track
            tunnelTracks = new BufferedImage[9];
            tunnelTracks[Game.BLUE] = ImageIO.read(Track.class.getResource("/Images/BlueTunnelTrack.png"));
            tunnelTracks[Game.RED] = ImageIO.read(Track.class.getResource("/Images/RedTunnelTrack.png"));
            tunnelTracks[Game.ORANGE] = ImageIO.read(Track.class.getResource("/Images/OrangeTunnelTrack.png"));
            tunnelTracks[Game.YELLOW] = ImageIO.read(Track.class.getResource("/Images/YellowTunnelTrack.png"));
            tunnelTracks[Game.GREEN] = ImageIO.read(Track.class.getResource("/Images/GreenTunnelTrack.png"));
            tunnelTracks[Game.PINK] = ImageIO.read(Track.class.getResource("/Images/PinkTunnelTrack.png"));
            tunnelTracks[Game.WHITE] = ImageIO.read(Track.class.getResource("/Images/WhiteTunnelTrack.png"));
            tunnelTracks[Game.BLACK] = ImageIO.read(Track.class.getResource("/Images/BlackTunnelTrack.png"));
            tunnelTracks[Game.ANY] = ImageIO.read(Track.class.getResource("/Images/GrayTunnelTrack.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Track(int color, int x, int y, float rotation, boolean tunnel) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.tunnel = tunnel;
    }

    public void paint(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform backup = g2.getTransform();
        AffineTransform a = AffineTransform.getRotateInstance(rotation, x, y);
        g2.setTransform(a);
        g2.drawImage(tracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);
        g2.setTransform(backup);
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
