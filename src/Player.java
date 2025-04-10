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
//     public boolean buyStation(int traincard1) { //if first station
//         if(trainCards[traincard1] > 0) {
//             trainCards[traincard1]--;
//             stations--;
//             return true; 
//         }
//         return false;
//   }
//     public boolean buyStation(int traincard1, int traincard2) { //if seocnd
//         if(trainCards[traincard1] > 0 ) {
//             trainCards[traincard1]--;
//             if(trainCards[traincard2] > 0 && traincard2 == traincard1) {
//                 trainCards[traincard2]--;
//                 stations--;
//             return true; 
//         }
//         trainCards[traincard1]++;
//         return false;
//     }
//     return false;
// }
//     public boolean buyStation(int traincard1, int traincard2, int traincard3) { // if thir d
//         if(trainCards[traincard1] > 0 ) //idk waht this is
//             trainCards[traincard1]--;
//         if(trainCards[traincard2] > 0 && traincard2 == traincard1) 
//             trainCards[traincard2]--;
            
//                 if(trainCards[traincard3] > 0 && traincard3 == traincard2) {
//                     trainCards[traincard3]--; 
//                     stations--;
//                 return true; 
//                 }
//             trainCards[traincard2]++;
//             trainCards[traincard1]++;
//             return false;
//             }
//             trainCards[traincard1]++;
//             return false;
//         }
//         return false;
//     }
    public boolean buyStation(int traincard1) { 
        int numNeeded = 0;
        if(stations == 3)//if building first station
             numNeeded = 1;
        else if(stations == 2)//if building second station
             numNeeded = 2;
        else if(stations == 1)//if building third/last station
             numNeeded = 3;
        if(trainCards[traincard1] >= numNeeded ) { //check if player has enough cards to build
            for(int i = 0; i < numNeeded; i++)
            {
                trainCards[traincard1]--;
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
                for(int i = 0; i < newlength-extralocomotives; i++){
                    Game.discardPile.add(buyingcolor);
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
            for(int i = 0; i < length-locomotivesused; i++){
                Game.discardPile.add(buyingcolor);
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

}
