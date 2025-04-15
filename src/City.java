import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
public class City
{
    private String name;
    private ArrayList<Route> routes;
    private int x;
    private int y;
    private Button button;
    private static BufferedImage image;
    private Player stationOwner;
    

    public static int SIZE = 25;
    static{
        try
        {
            image = ImageIO.read(City.class.getResource("/Images/City.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public City(String name, ArrayList<Route> routes, int x, int y)
    {
        this.name = name;
        this.routes = routes;
        this.x = x;
        this.y = y;
        stationOwner = null;
      
        this.button = new Button(GamePanel.MAP_X + x - SIZE/2, GamePanel.MAP_Y + y - SIZE/2, SIZE, SIZE, image);
    }
    public String getName()
    {
        return name;
    }

    public ArrayList<Route> getRoutes()
    {
        return routes;
    }
    public void addRoute(Route r)
    {
        routes.add(r);
    }

    public int getXCoord()
    {
        return x;
    }
    
    public int getYCoord()
    {
        return y;
    }
    public boolean hasStation() {
        if(stationOwner != null) {
            return true;
        }
        return false;
    }
    public Player getStationOwner() {
        return stationOwner;
    }
    public void addStationOwner(Player p) {
        stationOwner = p;
    }
    public Button getButton() {
        return button;
    }
    public void paint(Graphics g)
    {
        button.paint(g);
    }
}
