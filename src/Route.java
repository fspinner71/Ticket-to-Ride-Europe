import java.awt.*;

public class Route {

    private City a, b;
    private int length;
    private int color;
    private boolean tunnel;
    private int locomotivesRequired;
    private Player stationOwner;
    private boolean stationStart;
    private Player player;
    private Button[] buttons;
    private Track[] tracks;



    public Route(City a, City b, int length, boolean t, int loco, int color) {
        this.a = a;
        this.b = b;

        this.length = length;
        this.color = color;
        tunnel = t;
        locomotivesRequired = loco;

        buttons = new Button[length];
        stationOwner = null;
        player = null;
        stationStart = false;

        tracks = new Track[length];
    }
    public void makeTracks(int[][] coords)
    {
        if(coords.length != length) {
            return;
        }

        for(int i = 0; i < length; i++)
        {
            boolean l = false;
            if(i < locomotivesRequired)
            {
                l = true;
            }
            int x = coords[i][0];
            int y = coords[i][1];
            float rotation = (float) Math.toRadians(coords[i][2]);
            Track t = new Track(color, x, y, rotation, tunnel, l);
            addTrack(t);
        }
    }
    public void moveTrack(int i, int moveX, int moveY, int rot)
    {
        tracks[i].setX(tracks[i].getX() + moveX);
        tracks[i].setY(tracks[i].getY() + moveY);
        tracks[i].rotate((float)Math.toRadians(rot));
    }
    private void addPlayer(Player a) {

        player = a;

    }
    private void addStationOwner(Player a) {
        stationOwner = a;

    }


    public City getA() {
        return a;
    }
    public City getB() {
        return b;
    }

    public Player getPlayer() {
        return player;
    }
    public int getLength() {
        return length;
    }

    public int getColor() {
        return color;
    }

    public boolean hasStation() {
        if(stationOwner == null) {
            return false;
        }
    return true;
    }

    public int getLocomotivesRequired() {
        return locomotivesRequired;
    }
    public boolean isTunnel() {
        return tunnel;
    }
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.drawLine(a.getXCoord() + GamePanel.MAP_X, a.getYCoord() + GamePanel.MAP_Y, tracks[0].getX(), tracks[0].getY());
        g2.drawLine(b.getXCoord() + GamePanel.MAP_X, b.getYCoord() + GamePanel.MAP_Y, tracks[length-1].getX(), tracks[length-1].getY());
        for(int i = 0; i < length - 1; i++)
        {
            g2.drawLine(tracks[i].getX(), tracks[i].getY(), tracks[i+1].getX(), tracks[i+1].getY());
        }
        for(int i = 0; i < length; i++) {
            if (tracks[i] != null) {
                tracks[i].paint(g2);
            }
        }
    }

    public void addTrack(Track t)
    {
        for(int i = 0; i < tracks.length; i++)
        {
            if(tracks[i] == null) {
                tracks[i] = t;
                break;
            }
        }
    }
    public void changeSize(int size) {
        length = size;
        Track[] newTracks = new Track[size];
        tracks = newTracks;
    }
    public String toString()
    {
        String str = "";
        for(Track t : tracks)
        {
            str += t.getX() + "," + t.getY() + "," + t.getRotation() + ",";
        }
        return str;
    }
}
