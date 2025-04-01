public class Ticket{
    private String[] cities;
    private int points;
    private Button[] images = new Button[46];



    public Ticket(String one, String two, int p) {

        setCities(one, two);
        points = p;


    }
    public String[] getCities()
    {
        return cities;
    }
    public int getPoints()
    {
        return points;
    }
    public void setCities(String a, String b)
    {
        cities = new String[]{a, b};
    }
}
