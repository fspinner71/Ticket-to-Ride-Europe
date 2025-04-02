import java.io.*;
import java.net.URL;
import java.util.*;
public class Game {
    public static Player player1;
	public static Player player2;
	public static Player player3;
	public static Player player4;
    private Player players[] = new Player[4];
    private City cities[];
    private Route routes[];
    private int currentPlayer;
    private Stack<Ticket> tickets = new Stack<Ticket>();
    private Stack<Ticket> bigtickets = new Stack<Ticket>();
    private Stack<Integer> deck;
    public static int turn = 0;
    public static int shouldEnd = 0;
    private boolean drawnOne;
    public static final int RED = 0;
    public static final int ORANGE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int BLUE = 4;
    public static final int PINK = 5;
    public static final int WHITE = 6;
    public static final int BLACK = 7;
    public static final int ANY = 8;

    public Game(){


        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

        deck = new Stack<>();
        

        makeTickets();
        distributeTickets();

    }
    public static void drawCard(){

    }

    public void distributeTickets(){ 

        for(Player a: players) {

            a.addTicket(bigtickets.pop());
            a.addTicket(tickets.pop());
            a.addTicket(tickets.pop());
            a.addTicket(tickets.pop());

        }

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
}

        

