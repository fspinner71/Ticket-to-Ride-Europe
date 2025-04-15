public class Pair {
    private City city;
    private Route route;
    public Pair(City c, Route r)
    {
        city = c;
        route = r;
    }

    public City getCity()
    {
        return city;
    }

    public Route getRoute()
    {
        return route;
    }
}
