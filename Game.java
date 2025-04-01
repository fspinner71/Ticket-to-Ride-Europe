import java.util.Stack;

public class Game {
    private Player players[];
    private City cities[];
    private Route routes[];
    private int currentPlayer;
    private Stack<Ticket> tickets;
    private Stack<Integer> deck;
    public static int turn;
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
}
