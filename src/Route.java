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
    private float sign (float p1x, float p1y, float p2x, float p2y, float p3x, float p3y)
    {
        return (p1x - p3x) * (p2y - p3y) - (p2x - p3x) * (p1y - p3y);
    }

    private boolean PointInTriangle (float ptx, float pty, float v1x, float v1y, float v2x, float v2y, float v3x, float v3y)
    {
        float d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(ptx, pty, v1x, v1y, v2x, v2y);
        d2 = sign(ptx, pty, v2x, v2y, v3x, v3y);
        d3 = sign(ptx, pty, v3x, v3y, v1x, v1y);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }
    public boolean isInside(int Px, int Py)
    {
        for(int i = 0; i < length; i++)
        {
            Track t = tracks[i];
            float Ax, Ay, Bx, By, Cx, Cy, Dx, Dy;
            Ax = (int)((float)(-Track.WIDTH/2) * Math.cos(t.getRotation()) - (float)(-Track.HEIGHT/2) * Math.sin(t.getRotation())) + t.getX();
            Ay = (int)((float)(-Track.HEIGHT/2) * Math.cos(t.getRotation()) + (float)(-Track.WIDTH/2) * Math.sin(t.getRotation())) + t.getY();

            Bx = (int)((float)(Track.WIDTH/2) * Math.cos(t.getRotation()) - (float)(-Track.HEIGHT/2) * Math.sin(t.getRotation())) + t.getX();
            By = (int)((float)(-Track.HEIGHT/2) * Math.cos(t.getRotation()) + (float)(Track.WIDTH/2) * Math.sin(t.getRotation())) + t.getY();

            Cx = (int)((float)(Track.WIDTH/2) * Math.cos(t.getRotation()) - (float)(Track.HEIGHT/2) * Math.sin(t.getRotation())) + t.getX();
            Cy = (int)((float)(Track.HEIGHT/2) * Math.cos(t.getRotation()) + (float)(Track.WIDTH/2) * Math.sin(t.getRotation())) + t.getY();

            Dx = (int)((float)(-Track.WIDTH/2) * Math.cos(t.getRotation()) - (float)(Track.HEIGHT/2) * Math.sin(t.getRotation())) + t.getX();
            Dy = (int)((float)(Track.HEIGHT/2) * Math.cos(t.getRotation()) + (float)(-Track.WIDTH/2) * Math.sin(t.getRotation())) + t.getY();
            // double apd, dpc, cpb, pba;
            // //A = abs(0.5 * (x1(y2-y3) + x2(y3-y1) + x3(y1-y2)));
            // apd = Math.abs(0.5 * (Ax * (Py - Dy) + Px * (Dy - Ay) + Dx * (Ay - Py)));
            // dpc = Math.abs(0.5 * (Dx * (Py - Cy) + Px * (Cy - Dy) + Dx * (Cy - Py)));
            // cpb = Math.abs(0.5 * (Cx * (Py - By) + Px * (By - Cy) + Cx * (By - Py)));
            // pba = Math.abs(0.5 * (Px * (By - Ay) + Bx * (Ay - Py) + Px * (Ay - By)));

            // if(apd + dpc + cpb + pba <= Track.HEIGHT * Track.WIDTH)
            // {
            //     return true;
            // }


            if(PointInTriangle(Px, Py, Ax, Ay, Bx, By, Cx, Cy) || PointInTriangle(Px, Py, Ax, Ay, Dx, Dy, Cx, Cy))
            {
                System.out.println(i);
                return true;
            }
        }
        return false;

    public Button[] getButtons() {
        return buttons;
    }
}
