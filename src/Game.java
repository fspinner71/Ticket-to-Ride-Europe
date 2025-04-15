import java.io.*;
import java.net.URL;
import java.util.*;
import jdk.jshell.DeclarationSnippet;
import java.util.Scanner;
import java.util.ArrayList;
public class Game {
 
    private Player players[];
    private ArrayList<City> cities;
    private Route routes[];
    private int cards[];
    private Stack<Ticket> tickets = new Stack<Ticket>();
    private Stack<Ticket> bigtickets = new Stack<Ticket>();
    
    public static ArrayList<Integer> deck;
    public static int turn = 0;
    public static int shouldEnd = 0;
    public boolean drawnOne;
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int ORANGE = 4;
    public static final int PINK = 5;
    public static final int WHITE = 6;
    public static final int BLACK = 7;
    public static final int ANY = 8;
    public static ArrayList<Integer> discardPile;

    public Game(){
        try
        {
            cities = new ArrayList<City>();
            File citiesCSV = new File("src/csv/cities.csv"); //create file reader
            Scanner scanner = new Scanner(citiesCSV);
            String line = scanner.nextLine();
            String[] info = line.split(","); //array of the stuff in csv file
            for(int i = 0; i < info.length; i+=3)
            {
                String name = info[i]; //name of city
                int x = Integer.parseInt(info[i+1]); //x coord of city
                int y = Integer.parseInt(info[i+2]); //y coord of city
                ArrayList<Route> routes = new ArrayList<Route>(); //arraylist of routes for the city
                City temp = new City(name, routes, x, y); //create the city object
                cities.add(temp); //add to the array of cities
            }

        } catch(Exception e) {
            System.out.println(e);
        }
        players = new Player[4];
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();

        deck = new ArrayList<Integer>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 12; j++){
                deck.add(i);
            }
        }
        for(int i = 0; i < 14; i++){
            deck.add(ANY);
        }
        Collections.shuffle(deck);
        
        cards = new int[5]; //make cards
        for(int c = 0 ; c < 5; c++) {
            cards[c] = deck.get(0);
            deck.remove(0);
        }
        drawnOne = false;
        makeTickets();
        distributeTickets();

    }
    public void drawCard(int index ){ //0-4 is the face up cards, 5 is the deck/facedown card
        System.out.println("draw card");
        int card;
        if(index == 5) {
          if(deck.isEmpty() == false) {   card = deck.get(0); }
          else {
            //error
            return;
          }

  
           
            if(drawnOne == false ) {
                
                players[turn].addTrainCard(card);
                deck.remove(0);
                drawnOne = true;
                System.out.println("draw deck");
            }
            else if(drawnOne  == true) { // u end the turn 
            players[turn].addTrainCard(card);
            deck.remove(0);
            drawnOne = false;
            endTurn();
          


        }

    }
    
        else { 

            if(drawnOne == false) { //firs tturn
                 card = cards[index];
                if(card == ANY) { //if locomotive 
                    players[turn].addTrainCard(card);
                    replaceCard(index);
                    endTurn();
                }
                else { //colored card
                    System.out.println("draw one first turn");
                    players[turn].addTrainCard(card);
                    replaceCard(index);
                    drawnOne = true;
                    return;
                }


            }   

        else if(drawnOne == true) { //if second turn 
            System.out.println("second turn");
             card = cards[index];
                if(card == ANY) { //if locomotive 
                    //do error
                    
                }
                else { //any other card and then u end the turnrnrnrnrnr
                    System.out.println("draw one second  turn");
                    players[turn].addTrainCard(card);
                    replaceCard(index);
                    drawnOne = false;
                    endTurn();
                    
                }


        }


        }

        }


        public Ticket[] drawTicket()    { //returns array of 4 tickets 

            Ticket[] a = new Ticket[4];
            for(int c = 0 ;c < 4; c++) {
                a[c] = tickets.pop();
            }
            return a;
    
        }
    
        public void replaceTicket(Ticket a) { // replaes a ticket 
            tickets.push(a);
        }
    public ArrayList<City> getCities() { //returns the array of cities
        return cities;
    }

    public int[] getFaceUpCards() {
        return cards;
    }
    public ArrayList<Integer> getDeck() {
        return deck;
    }
    public void buyRoute(Route p, int locomotivesused, int buyingcolor) { // except tunel

        if(locomotivesused < players[turn].getNumLocomotives()) { //if they dont even have enough locomotivs 
            //error panel
           

        }
        else if (p.getColor() != ANY && p.getColor() != buyingcolor) { //if route is not a grey route and the color doesnt match what they tryna buy wth yk
            //error pael
        }


        else {
            boolean a = players[turn].buyRoute(p, locomotivesused, buyingcolor);
            if(a) { // if they buy the route mvoe the turn
                endTurn();


            }
        }
        


    }

    public void buyStation(City a, int color) { //city the player wants to plae the station on
        if(a.hasStation()) {
            //error panel city alr has station
            return;
        }
        
        if(players[turn].buyStation(color)) { //if they can buy it with the color they chooese 
            a.addStationOwner(players[turn]);
            endTurn();

        }
        else {
            //error panel not enough cards
        }

    }


    public void endTurn() { //move turn and check if u need to end game 
        System.out.println("turn eneded");
        if(players[turn].getNumTrains() <= 2 || shouldEnd>0) {  //if game needs ot end 
            shouldEnd++; //????
        }
        if(shouldEnd == 4) { //everyone finsihed their one turn 
            //END GAME 

        }
        turn++; 
        turn = turn % 4;



    }
/* idk how we should do 
    public boolean buyTunnel(Route p, int locomotivesused, int buyingcolor) { //returns true if u succesfully buy it reutnr false if at least one card matches 
        int[] threecards = new int[3];
            for(int c  = 0; c < 3; c++) { //get the 3 drawn cards from the deck, if deck is empty and u cant draw it then it becomes -1;
                if(deck.isEmpty() == false) {
                threecards[c] = deck.get(0);
                }
                else {
                    threecards[c] = -1;
                }
            }
            int nummatching = 0;
            for(int c = 0; c < threecards[c]; c++){ 
                if(threecards[c] == buyingcolor) {
                    nummatching++; // how many cards match
                }

            
            }
            if(nummatching > 0) {
                return false;
            }
    }
    
*/

    public void distributeTickets(){ 

        for(Player a: players) {

            a.addTicket(bigtickets.pop());
            a.addTicket(tickets.pop());
            a.addTicket(tickets.pop());
            a.addTicket(tickets.pop());

        }


    }
    public void placeStation(){

    }
    
    public void makeTickets() {
        tickets = new Stack<Ticket>(); //temporary patron deck that will contain all patrons from the csv file
        bigtickets = new Stack<Ticket>();
        
        String line; 

        try {
           
            URL tem = Game.class.getResource("/csv/smalltickets.csv"); //create file reader
            BufferedReader r = new BufferedReader(new InputStreamReader(tem.openStream()));
         
            while((line = r.readLine()) != null) {
                String[] info = line.split(","); //array of the stuff in csv file
                
               
                int points;

              

                    points = Integer.parseInt(info[2]); //convert to int
                
                
                Ticket temp = new Ticket(info[0], info[1], points);
                tickets.push(temp); // add normal tickets
                
            }

            URL big = Game.class.getResource("/csv/bigticket.csv"); //create file reader
            BufferedReader big2 = new BufferedReader(new InputStreamReader(big.openStream()));
         
            while((line = big2.readLine()) != null) {
                String[] info = line.split(","); //array of the stuff in csv file
                
               
                int points;

              

                    points = Integer.parseInt(info[2]); //convert to int
                
                
                Ticket temp = new Ticket(info[0], info[1], points);
                bigtickets.push(temp); // add big tickets
                
            }
            Collections.shuffle(bigtickets);
            Collections.shuffle(tickets);
        }
        catch( Exception E){
            System.out.println("tickets dont work ");
          
        }
    }
    public void allPoints(){
        int totalpoints = 0;
        for(int i = 0; i < 4; i++){
            //totalpoints += (countTickets(players[i]));
        }
    }

    public void replaceCard(int index) { //method to replace face up card with face down card
        if(deck.isEmpty() == false) {
            cards[index] = deck.get(0);
            deck.remove(0);
        }

    }

    public void countTickets() {



    }
                                                                                                                                                
   
    
}


        

