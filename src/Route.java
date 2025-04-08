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



    public Route(City a, City b, int length, boolean t, int loco) {
        this.a = a;
        this.b = b;

        this.length = length;
        tunnel = t;
        locomotivesRequired = loco;

        buttons = new Button[length];
        stationOwner = null;
        player = null;
        stationStart = false;

        tracks = new Track[length];
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
        for(int i = 0; i < length; i++) {
            if (tracks[i] != null) {
                tracks[i].paint(g);
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
}
