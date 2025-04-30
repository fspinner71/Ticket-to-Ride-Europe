import java.util.*;
public class Player {

    private int trains;
    private int[] trainCards;
    private ArrayList<Ticket> tickets;
    
    private ArrayList<Route> routes;
    private int stations;
    private Map<City, ArrayList<Pair>> adjacencyList;

    private int points;


    public Player() {
        trains = 45;
        trainCards = new int[9];
        tickets = new ArrayList<>();
        routes = new ArrayList<>();
        stations = 3;
        adjacencyList = new TreeMap<City, ArrayList<Pair>>();
        points = 0;

    }
    public void addRoute(Route r ) {
        routes.add(r);
        //adjacencyList.get(r.getA()).add(new Pair(r.getA(), r)); //fix 
    }
    public int getScore()
    {
        return 1;
    }
    
    public void addTrainCard(int color) {
        trainCards[color]++;
    }

    public void addTicket(Ticket a) {
        tickets.add(a);
    }

    public boolean buyStation(int traincard1) { // if thir d
        int numNeeded = 0;
        if(stations == 0) {
            return false;
        }
        if(stations == 3)
             numNeeded = 1;
        else if(stations == 2)//if building second station
             numNeeded = 2;
        else if(stations == 1)//if building third/last station
             numNeeded = 3;
        if(trainCards[traincard1] >= numNeeded ) { //idk waht this is
            trainCards[traincard1] -= numNeeded;
            for(int c = 0; c < numNeeded; c++) {
                Game.discardPile.add(traincard1); // add to discard pile
            }
            stations--;
            return true;
        }
        return false;
    }
    
    public boolean buyRoute(Route p, int locomotivesused, int buyingcolor) { //buying color is what color u tryna buy with if its a grey route
        //we will make sure buying color is same as route color if route has a color in the game class or wtv so they cant buy like a red route with a blue card



        // buying tunnel is in game clas

        
        if(p.getLocomotivesRequired() > 0) { //if it is a fery
       
            int length = p.getLength();
            int num = p.getLocomotivesRequired();
            if(trainCards[8] < p.getLocomotivesRequired()) {
                return false; // u dont have enough lcoomotive s
            }

            int extralocomotives = locomotivesused-num; //the extra num of locomtoives the player used so like the not required ones if they decide to use more
            int newlength = length - num; // new length

            if(trainCards[buyingcolor] + extralocomotives < newlength) {
                return false; //u cant buy
            }
            else {
                addRoute(p);
                trainCards[buyingcolor] -= newlength-extralocomotives; //u buy the route
                trainCards[Game.ANY] -= locomotivesused; 
                for(int i = 0; i < newlength-extralocomotives; i++){
                    Game.discardPile.add(buyingcolor);
                }
                for(int i = 0; i < locomotivesused; i++) {
                    Game.discardPile.add(Game.ANY); //add locomotives to disacrd
                }
                trainCards[8] -= extralocomotives;
                trains -= newlength;
                return true;
            }

        }//end of if ferry
        //if regular route

       
        int length = p.getLength();

        if(trainCards[buyingcolor] + locomotivesused < length) {
            return false; //u cant buy
        }
        else {
            addRoute(p);
            trainCards[buyingcolor] -= length-locomotivesused; //u buy the route
            trainCards[Game.ANY] -= locomotivesused;
            for(int i = 0; i < length-locomotivesused; i++){
                Game.discardPile.add(buyingcolor);
            }
            for(int i = 0; i < locomotivesused; i++) {
                Game.discardPile.add(Game.ANY); //add locomotives to disacrd
            }
            trainCards[8] -= locomotivesused;
            trains -= length;
            return true;
        
        }

    }
    //sdfsdfsdf

    public int getNumTrains() {
        return trains;
    }
    public int[] getTrainCards() {
        return trainCards;
    }
    public int getNumTrainCards() {
        int count = 0;
        for(int c = 0; c< trainCards.length; c++) {
            count+= trainCards[c];
        }
        return count;
    }
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    public int getNumStations() {
        return stations; 
    }
    public int getPoints() {
        int count = 0;
        for(Route r : routes) {
            if(r.getLength() == 1) {
                count++;
            }
            if(r.getLength() == 2) {
                count +=2; 
            }
            if(r.getLength() == 3) {
                count+=4;
            }
            if(r.getLength() == 4) {
                count+=7;
            }
            if(r.getLength() == 6) {
                count+=15;
            }
            if(r.getLength() == 8) {
                count+= 21;
            }
        }
        count += addTicketPoints(); //add points gotten from completing tickets
        return count;
    }
    public int addTicketPoints()
    {
        int ticketPoints = 0;
        for(Ticket ticket : getTickets()) {
            if(findRoute(ticket.getCities()[0], null, ticket.getCities()[1]))
            {
                ticketPoints += ticket.getPoints();
            }
        }
        return ticketPoints;
    }
    public ArrayList<Route> getRoutes() {
        return routes;

    }

   public int getNumLocomotives() {
    return trainCards[Game.ANY];
   }


   public ArrayList<City> playerCities() {
        ArrayList<City> cities = new ArrayList<City>();
        for(Route r: routes)
        {
            cities.add(r.getA());
            cities.add(r.getB());
        }
        return cities;

   }

   //returns true if player has completed the ticket
    public boolean findRoute(String s, City p, String e) //s is 'starting' city, p is previous city, e is 'ending' city; change ticket to hold city vars and not string??
    { 
        City start = null;
        City end = null;

        for(City city: playerCities())
        {
            if(city.getName().equals(s))
            {
                start = city;
            }
            else if(city.getName().equals(e))
            {
                end = city;
            }
        }
        if(start != null && end != null)
        {
            for(Pair pairs: adjacencyList.get(start))
            {
                Route r = pairs.getRoute();
                if(r.getA().equals(start) || r.getB().equals(start)) //the route has the city
                {
                    City other = r.getB();
                    if(other.equals(start))
                    {
                        other = r.getA(); //r.getB() is the start, so other would be set to r.getA()
                    }
                    if(other.equals(end))
                    {
                        return true; //route found
                    }
                    else if(!other.equals(p))//it's the same route from previous recursion, just backwards
                        {
                            findRoute(other.getName(), start, end.getName()); //continue search with 'other' as start city                        }                   
                        }
                }
            
            }
        }
        return false; //not found
    }

    public int numOfColor(int color)
    {
        return trainCards[color];
    }



   

    
}
