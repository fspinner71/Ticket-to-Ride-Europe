import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class Track {
    public static final int WIDTH = 42;
    public static final int HEIGHT = 15;
    public static final int CAR_WIDTH = 42;
    public static final int CAR_HEIGHT = 4;
    private static BufferedImage tracks[];
    private static BufferedImage tunnelTracks[];
    public static BufferedImage owns[];
    private int color;
    private int x, y;
    private float rotation;
    private boolean tunnel;
    private boolean locomotive;

    static {
        tracks = new BufferedImage[10];
        tunnelTracks = new BufferedImage[10];
        owns = new BufferedImage[4];
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
            tracks[9] = ImageIO.read(Track.class.getResource("/Images/LocomotiveTrack.png")); 

            //tunnel track
            tunnelTracks[Game.BLUE] = ImageIO.read(Track.class.getResource("/Images/BlueTunnelTrack.png"));
            tunnelTracks[Game.RED] = ImageIO.read(Track.class.getResource("/Images/RedTunnelTrack.png"));
            tunnelTracks[Game.ORANGE] = ImageIO.read(Track.class.getResource("/Images/OrangeTunnelTrack.png"));
            tunnelTracks[Game.YELLOW] = ImageIO.read(Track.class.getResource("/Images/YellowTunnelTrack.png"));
            tunnelTracks[Game.GREEN] = ImageIO.read(Track.class.getResource("/Images/GreenTunnelTrack.png"));
            tunnelTracks[Game.PINK] = ImageIO.read(Track.class.getResource("/Images/PinkTunnelTrack.png"));
            tunnelTracks[Game.WHITE] = ImageIO.read(Track.class.getResource("/Images/WhiteTunnelTrack.png"));
            tunnelTracks[Game.BLACK] = ImageIO.read(Track.class.getResource("/Images/BlackTunnelTrack.png"));
            tunnelTracks[Game.ANY] = ImageIO.read(Track.class.getResource("/Images/GrayTunnelTrack.png"));
            tunnelTracks[9] = ImageIO.read(Track.class.getResource("/Images/LocomotiveTunnelTrack.png"));

            owns[0] = ImageIO.read(Track.class.getResource("/Images/BlueOwns.png"));
            owns[1] = ImageIO.read(Track.class.getResource("/Images/RedOwns.png"));
            owns[2] = ImageIO.read(Track.class.getResource("/Images/GreenOwns.png"));
            owns[3] = ImageIO.read(Track.class.getResource("/Images/YellowOwns.png"));


            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Track(int color, int x, int y, float rotation, boolean tunnel, boolean locomotive) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.tunnel = tunnel;
        this.locomotive = locomotive;
    }

    public void paint(Graphics g, int owned) 
    {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform backup = g2.getTransform();
        AffineTransform a = AffineTransform.getRotateInstance(rotation, x, y);
        g2.setTransform(a);
        if(tunnel)
        {
            if(locomotive)
            {
                g2.drawImage(tunnelTracks[9], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);

            } else {
                g2.drawImage(tunnelTracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);
            }
        } else {
            if(locomotive)
            {
                g2.drawImage(tracks[9], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);

            } else {
                g2.drawImage(tracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);

            }
        }

        if(owned > -1)
        {
            g2.drawImage(owns[owned], x - CAR_WIDTH/2, y - CAR_HEIGHT/2, CAR_WIDTH, CAR_HEIGHT, null);
        }
        g2.setTransform(backup);
    }

    public void paintOffset(Graphics2D g2, int owned, int Ox, int Oy) 
    {
        AffineTransform backup = g2.getTransform();
        // AffineTransform a = AffineTransform.getRotateInstance(rotation, x, y);
        // g2.setTransform(a);

        g2.rotate(rotation, x, y);
        if(tunnel)
        {
            if(locomotive)
            {
                g2.drawImage(tunnelTracks[9], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);

            } else {
                g2.drawImage(tunnelTracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);
            }
        } else {
            if(locomotive)
            {
                g2.drawImage(tracks[9], x - WIDTH/2 , y - HEIGHT/2, WIDTH, HEIGHT, null);

            } else {
                g2.drawImage(tracks[color], x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT, null);

            }
        }

        if(owned > -1)
        {
            g2.drawImage(owns[owned], x - CAR_WIDTH/2, y - CAR_HEIGHT/2, CAR_WIDTH, CAR_HEIGHT, null);
        }
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
