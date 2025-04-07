import java.io.*;
import java.net.URL;
import java.util.*;
import jdk.jshell.DeclarationSnippet;
public class Game {
 
    private Player players[] = new Player[4];
    private City cities[];
    private Route routes[];
    private int cards[];
    private Stack<Ticket> tickets = new Stack<Ticket>();
    private Stack<Ticket> bigtickets = new Stack<Ticket>();
    public static ArrayList<Integer> deck;
    public static int turn = 0;
    public static int shouldEnd = 0;
    private boolean drawnOne;
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

        makeTickets();
        distributeTickets();

    }
    public void drawCard(int index ){ //0-4 is the face up cards, 5 is the deck/facedown card

        if(index == 5) {
            int card = deck.get(0); 
            players[turn].addTrainCard(card);
            if(card == ANY){ //if locomotive 
                turn ++; //end turn
                turn = turn % 4;
            }

        }
        else { 

            if(drawnOne == false) { //firs tturn
                int card = cards[index];
                if(card == ANY) { //if locomotive 
                    players[turn].addTrainCard(card);
                    replaceCard(index);
                    turn++; 
                    turn = turn % 4;
                }
                else { //colored card
                    players[turn].addTrainCard(card);
                    replaceCard(index);
                    drawnOne = true;

                }


            }


        }

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
    public void allPoints(){
        
    }

    public void replaceCard(int index) { //method to replace face up card with face down card
        if(deck.isEmpty() == false) {
            cards[index] = deck.get(0);
            deck.remove(0);
        }

    }


}

        

