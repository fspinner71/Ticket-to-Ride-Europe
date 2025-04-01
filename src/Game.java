import java.util.Stack;
import java.util.*;
public class Game {
    public static Player player1;
	public static Player player2;
	public static Player player3;
	public static Player player4;
    private Player players[] = new Player[4];
    private City cities[];
    private Route routes[];
    private Stack<Ticket> tickets = new Stack<Ticket>();
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
        
    }
    public static void drawCard(){

    }
}
