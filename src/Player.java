import java.util.*;
public class Player {

    private int trains;
    private int[] trainCards;
    private ArrayList<Ticket> tickets;
    
    private ArrayList<Route> routes;
    private int stations;

    public void addRoute(Route r ) {


    }
    public void addTrainCard(int color) {

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
    }
    public ArrayList<Route> getRoutes() {
        return routes;

    }

}
