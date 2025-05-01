import java.io.*;
import java.net.URL;
import java.util.*;
import jdk.jshell.DeclarationSnippet;
public class Game {
 
    public Player players[];
    private ArrayList<City> cities;
    private ArrayList<Route> routes;
    private int cards[];
    private Stack<Ticket> tickets = new Stack<Ticket>();
    private Stack<Ticket> bigtickets = new Stack<Ticket>();
    
    public static ArrayList<Integer> deck;
    public static int turn = 0;
    public static int shouldEnd = 0;
    public boolean drawnOne, errorPanel, turnended;
    public String errorMessage;
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
    public boolean buyTunnel = false;
    public Game(){
        try
        {
            cities = new ArrayList<City>();
            
            File citiesCSV = new File("src/csv/cities.csv"); //create file reader
            Scanner scanner = new Scanner(citiesCSV);
            String line = scanner.nextLine();
            String[] info = line.split(","); //array of the stuff in csv file
            for(int i = 0; i < info.length; i+=5)
            {
                String name = info[i]; //name of city
                int x = Integer.parseInt(info[i+1]); //x coord of city
                int y = Integer.parseInt(info[i+2]); //y coord of city
                int nameX = Integer.parseInt(info[i+3]);
                int nameY = Integer.parseInt(info[i+4]);
                ArrayList<Route> routes = new ArrayList<Route>(); //arraylist of routes for the city
                City temp = new City(name, routes, x, y, nameX, nameY); //create the city object
                cities.add(temp); //add to the array of cities
            }

            routes = new ArrayList<Route>();
            File routesCSV = new File("src/csv/routes.csv"); //create file reader
            Scanner routeScanner = new Scanner(routesCSV);
            String routeLine = routeScanner.nextLine();
            String[] routeInfo = routeLine.split(","); //array of the stuff in csv file
            for(int i = 0; i < routeInfo.length; i+=6)
            {
                String city1Name = routeInfo[i];
                String city2Name = routeInfo[i+1];
                City city1 = null, city2 = null;
                int color = Integer.parseInt(routeInfo[i+2]);
                boolean tunnel = Boolean.parseBoolean(routeInfo[i+3]);
                int locomotives = Integer.parseInt(routeInfo[i+4]);
                int length = Integer.parseInt(routeInfo[i+5]);
                int[][] trackInfo = new int[length][3];
                for(int j = 0; j < length; j++)
                {
                    trackInfo[j][0] = Integer.parseInt(routeInfo[i+6]);
                    trackInfo[j][1] = Integer.parseInt(routeInfo[i+7]);
                    trackInfo[j][2] = (int)Math.toDegrees(Float.parseFloat(routeInfo[i+8]));
                    i += 3;
                }

                for(City c : cities)
                {
                    if(c.getName().equals(city1Name))
                    {
                        city1 = c;
                    } else if(c.getName().equals(city2Name))
                    {
                        city2 = c;
                    }
                }

                Route r = new Route(city1, city2, length, tunnel, locomotives, color);
                r.makeTracks(trackInfo);
                routes.add(r);
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
        turnended = false;
        discardPile = new ArrayList<Integer>();
        makeTickets();
        distributeTickets();
        players[0].addTrainCard(0);
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
            errorPanel = false;
            errorMessage = "";
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
          
                }


            }   

        else if(drawnOne == true) { //if second turn 
            System.out.println("second turn");
             card = cards[index];
                if(card == ANY) { //if locomotive 
                    errorScreen("You can't draw a locomotive!");
                    
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
        
                            int lococount = 0;
        for(int c = 0; c<  cards.length; c++) {
            if(cards[c] == Game.ANY) {
                lococount++;
            }
        }
        if(lococount >= 3) {
            System.out.println("alalsdpasdfsdfsdfddd");
            for(int c = 0; c < cards.length; c++ ) {
                if(cards[c] == Game.ANY) {
                    discardPile.add(cards[c]);
                    replaceCard(c);
                    errorScreen("there is more than 3 locomotives");
                }
            }
            
        }

        }
        
        public void errorScreen(String error) {

            System.out.println("error panel pops up");
        errorPanel = true;
        errorMessage = error;

        }
        public void unerror() {

            System.out.println("closes");
            errorPanel = false;
            errorMessage = "";
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
    public ArrayList<Route> getRoutes()
    {
        return routes;
    }

    public int[] getFaceUpCards() {
        return cards;
    }
    public ArrayList<Integer> getDeck() {
        return deck;
    }
    public void buyRoute(Route p, int locomotivesused, int buyingcolor) { // except tunel

        if(locomotivesused < players[turn].getNumLocomotives()) { //if they dont even have enough locomotivs 
            errorScreen("Don't have enough locomotives!");
           

        }
        else if (p.getColor() != ANY && p.getColor() != buyingcolor) { //if route is not a grey route and the color doesnt match what they tryna buy wth yk
            errorScreen("Color doesn't match!");
        }


        else {
            boolean a = players[turn].buyRoute(p, locomotivesused, buyingcolor);
            if(a) { // if they buy the route mvoe the turn
                endTurn();


            }
            else {
                errorScreen("too broke");
            }
        }
        


    }

    public void buyStation(City a, int color) { //city the player wants to plae the station on
        if(a.hasStation()) {
            errorScreen("already has a station");
            return;
        }
        
        if(players[turn].buyStation(color)) { //if they can buy it with the color they chooese 
            a.addStationOwner(players[turn]);
            endTurn();

        }
        else {
            errorScreen("dont have enoguh cards or smtj isdfosdfodsf");
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
        turnended = true;


    }
    public boolean buyTunnel(Route p, int locomotivesused, int buyingcolor) { //buys the tunnel
        if(locomotivesused > players[turn].getNumLocomotives())
        {
            errorMessage = "No more locomotives!";
            errorPanel = true;
            return false;
        }
        if(buyingcolor > players[turn].numOfColor(buyingcolor))
        {
            errorMessage = "No more cards!";
            errorPanel = true;
            return false;
        }
        int[] threecards = new int[3];
        int nummatching = 0;
            for(int c  = 0; c < 3; c++) { //get the 3 drawn cards from the deck, if deck is empty and u cant draw it then it becomes -1;
                if(deck.isEmpty() == false) {
                threecards[c] = deck.get(0);
                }
                else {
                    threecards[c] = -1;
                }
            }
            for(int c = 0; c < threecards[c]; c++){ 
                if(threecards[c] == buyingcolor) {
                    nummatching++; // how many cards match
                }
            }
            if(locomotivesused + buyingcolor > nummatching)
            {
                errorMessage = "Too Much!";
                errorPanel = true;
            }
            else if(locomotivesused + buyingcolor == nummatching)
            {
                players[turn].buyTunnel(p, locomotivesused, buyingcolor);
                return true;
            }
            return false;               
           
    }
    


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
    
    public Player[] getPlayers()
 {
    return players;
 }    public void replaceCard(int index) { //method to replace face up card with face down card
        if(deck.isEmpty() == false) {
            cards[index] = deck.get(0);
            deck.remove(0);
        }

    }

    //point counting stuff moved to player class
}


        

