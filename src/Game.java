import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private Stack<Integer> deck;
    public static int turn = 0;
    private int shouldEnd;
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
        
    }
    public static void drawCard(){

    }

    public void makeTickets() {
        tickets = new Stack<Ticket>(); //temporary patron deck that will contain all patrons from the csv file
     
        
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
}

        

