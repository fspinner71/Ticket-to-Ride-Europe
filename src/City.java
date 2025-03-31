public class City
{
    private String name;
    private ArrayList<Route> routes;
    private int x;
    private int y;
    private Button cityButton;

    public City(String name, ArrayList<Route> routes, int x, int y, BufferedImage image)
    {
        this.name = name;
        this.routes = routes;
        this.x = x;
        this.y = y;
        this.cityButton = new Button(x, y, 50, 50, image);
    }
    public String getName()
    {
        return name;
    }

    public ArrayList<Route> getRoutes()
    {
        return routes;
    }

    public int getXCoord()
    {
        return x;
    }
    
    public int getYCoord()
    {
        return y;
    }
}
