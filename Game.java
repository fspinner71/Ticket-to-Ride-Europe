import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java..URL;
public class Game {
    private Player players[];
    private City cities[];
    private Route routes[];
    private int currentPlayer;
    private Stack tickets<Tickets>;



    public void makeTickets() {
        tickets = new Stack<Tickets>(); //temporary patron deck that will contain all patrons from the csv file
     
        
        String line; 

        try {
           
            URL tem = Game.class.getResource("/csv/csvpatron.csv"); //create file reader
            BufferedReader r = new BufferedReader(new InputStreamReader(tem.openStream()));
         
            while((line = r.readLine()) != null) {
                String[] info = line.split(","); //array of the stuff in csv file
                
               
                int points;

              

                    points = Integer.parseInt(info[2]); //convert to int
                
                
                Ticket temp = new Ticket(info[0], info[1], points);
                tickets.push(temp); // add patron
                
            }
            
        }
        catch( Exception E){
            System.out.println("smallticket dont work ");
          
        }

        
}
