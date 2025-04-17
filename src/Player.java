import java.util.*;
public class Player {

    private int trains;
    private int[] trainCards;
    private ArrayList<Ticket> tickets;
    
    private ArrayList<Route> routes;
    private int stations;


    public Player() {
        trains = 45;
        trainCards = new int[9];
        tickets = new ArrayList<>();
        routes = new ArrayList<>();
        stations = 3;


    }
    public void addRoute(Route r ) {
        routes.add(r);
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
        return count;
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

}
