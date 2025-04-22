import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
public class GamePanel extends JPanel implements MouseListener,KeyListener {

    private static BufferedImage map, city, deck, ticket;
    public static BufferedImage[] cards, buttons;
    private Button[] actions, gameCards;
    public static BufferedImage bottomBar, leftBar, rightBar, upArrow, errorWindow, notebookPaper, textBoxLarge, textBoxSmall;     
    public static BufferedImage[] stations, owns;
    public static BufferedImage locomotiveTrack, locomotiveTunnelTrack; //extra stuff 
    public static final int MAP_X = 305;
    public static final int MAP_Y = -10;
    private static Font font;
    private Button back, okButton;
    private int action; // 0 = hasnt picjed, 1 = draw, 2 = route, 3 = station, 4 = ticket
    private Game game;

    private City city1, city2;
    private int currentSize;
    private boolean tunnel;
    private boolean flipped;
    private int locomotives;
    private int color;
    private int step;
    private Route currentRoute;
    private PrintWriter writer;
    private ArrayList<Route> routes;

    static {
        font = new Font("Comic Sans MS", Font.BOLD, 24);

        try
        {
            //main game assets
            map = ImageIO.read(GamePanel.class.getResource("/Images/Map.png"));
            city = ImageIO.read(GamePanel.class.getResource("/Images/City.png"));
            deck = ImageIO.read(GamePanel.class.getResource("/Images/Deck.png"));
            ticket = ImageIO.read(GamePanel.class.getResource("/Images/Ticket.png"));
            locomotiveTrack = ImageIO.read(GamePanel.class.getResource("/Images/LocomotiveTrack.png"));
            locomotiveTunnelTrack = ImageIO.read(GamePanel.class.getResource("/Images/LocomotiveTunnelTrack.png"));

            //stations
            stations = new BufferedImage[4]; //order of stations and players: red blue yellow green 
            stations[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedStation.png"));
            stations[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueStation.png"));
            stations[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowStation.png"));
            stations[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenStation.png"));

            //owns cars thing
            owns = new BufferedImage[4];
            owns[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueOwns.png"));
            owns[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedOwns.png"));
            owns[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenOwns.png"));
            owns[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowOwns.png"));

            //cards
            cards = new BufferedImage[9];
            cards[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedCard.png"));
            cards[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueCard.png"));
            cards[Game.ORANGE] = ImageIO.read(GamePanel.class.getResource("/Images/OrangeCard.png"));
            cards[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowCard.png"));
            cards[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenCard.png"));
            cards[Game.PINK] = ImageIO.read(GamePanel.class.getResource("/Images/PinkCard.png"));
            cards[Game.WHITE] = ImageIO.read(GamePanel.class.getResource("/Images/WhiteCard.png"));
            cards[Game.BLACK] = ImageIO.read(GamePanel.class.getResource("/Images/BlackCard.png"));
            cards[Game.ANY] = ImageIO.read(GamePanel.class.getResource("/Images/Locomotive.png"));

            //gui stuf f
            bottomBar = ImageIO.read(GamePanel.class.getResource("/Images/BottomBar.png"));
            leftBar = ImageIO.read(GamePanel.class.getResource("/Images/LeftBar.png"));
            rightBar = ImageIO.read(GamePanel.class.getResource("/Images/RightBar.png"));
            upArrow = ImageIO.read(GamePanel.class.getResource("/Images/UpArrow.png"));
            errorWindow = ImageIO.read(GamePanel.class.getResource("/Images/ErrorWindow.png"));
            notebookPaper = ImageIO.read(GamePanel.class.getResource("/Images/NotebookPaper.png"));
            textBoxLarge = ImageIO.read(GamePanel.class.getResource("/Images/TextBoxLarge.png"));
            textBoxSmall = ImageIO.read(GamePanel.class.getResource("/Images/textBoxSmall.png"));

            //gui stuff buttons
            buttons = new BufferedImage[6];
            buttons[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedButton.png"));
            buttons[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueButton.png"));
            buttons[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowButton.png"));
            buttons[Game.ORANGE] = ImageIO.read(GamePanel.class.getResource("/Images/OrangeButton.png"));
            buttons[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenButton.png"));
            buttons[Game.PINK] = ImageIO.read(GamePanel.class.getResource("/Images/PinkButton.png"));
            
            deck = ImageIO.read(GamePanel.class.getResource("/Images/Deck.png")); 
        } catch (Exception e)
        {
            System.out.println("Failed to load GamePanel images");
        }
    }

    public GamePanel()
    {
        game = new Game();
        actions = new Button[4];
        gameCards = new Button[6]; //like when you draw cards wtv it has includes the face down card ;; 0 = facedown, 1-5 = cards

        gameCards[0] = new Button(1645, 115 , cards[0].getWidth()/5, cards[0].getHeight()/5, deck); //deck button
        for(int c = 1; c < gameCards.length; c++) {
            gameCards[c] = new Button(1645, 115 + c * 135, cards[0].getWidth()/5, cards[0].getHeight()/5, deck); //face up cards button image is default
                }
        actions[0] = new Button(1600, 300, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.BLUE]);
        actions[1] = new Button(1600, 410, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.YELLOW]);
        actions[2] = new Button(1600, 520, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.PINK]);
        actions[3] = new Button(1600, 630, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.ORANGE]);
            back = new Button(1600, 925, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.RED]);
            okButton = new Button(775, 575, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.GREEN]);
       
        addMouseListener(this);
        addKeyListener(this);

        try {
            writer = new PrintWriter("routes.csv", "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }

        routes = new ArrayList<Route>();
    }
    @Override
    public void paint(Graphics g)
    {
       
        if(game.errorPanel == true) {
            g.drawImage(errorWindow, 725, 300, errorWindow.getWidth()/3, errorWindow.getHeight()/3, null);
            okButton.paint(g);
        }
        else {
        g.drawImage(map, MAP_X, MAP_Y, map.getWidth(), map.getHeight(), null);
        g.drawImage(bottomBar, getWidth()/2 - bottomBar.getWidth()/2, getHeight() - bottomBar.getHeight(), bottomBar.getWidth(), bottomBar.getHeight(), null);
        g.drawImage(leftBar, 0, 0, leftBar.getWidth(), leftBar.getHeight(),null);
        g.drawImage(rightBar, getWidth() - rightBar.getWidth(), 0, rightBar.getWidth(), rightBar.getHeight(),null);

        g.drawString(Integer.toString(game.turn), 100, 200);



        if(action == 0) {
        for(Button a : actions) { //paint actions if they havent picked yet  
            a.paint(g);
        }
    }
    if(action == 1) { //if draw card 
        for(int c = 1; c < gameCards.length; c++) { //paint faceup
            int[] faceup = game.getFaceUpCards();
         
            gameCards[c].setImage(cards[faceup[c-1]]);
            gameCards[c].paint(g);
        }
        if(game.getDeck().isEmpty() == false) { //paint deck
          gameCards[0].paint(g);
           
        }

        back.paint(g);

    }

    

        

        ArrayList<City> cities = game.getCities();
        for(int i = 0; i < cities.size(); i++)
        {
            cities.get(i).paint(g);
        }
    }
}
    public void mousePressed(MouseEvent e)
    {
      
        
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            int x = e.getX();
            int y = e.getY();

            if(game.errorPanel == true) {
                
                if(okButton.isInside(x, y)) {
                    game.unerror(); //stop error
                }

            }
            else {// not error 

            


            for(int i = 0; i < game.getCities().size(); i++)
            {
                City city = game.getCities().get(i);
                if(city.getButton().isInside(x, y))
                {
                    if(step == 0) //select first city
                    {
                        city1 = city;
                        step++;
                    }
                    else if(step == 1) //select second city
                    {
                        city2 = city;
                        step++;
                    }

                    break;
                }
            }
            if(action == 0) {
          for(int c = 0 ;c <  actions.length; c++) {
            if(actions[c].isInside(x, y)) {
                action = c+1; //change turn wtv they click 
                repaint();
            }
          }

        }
        else if(action == 1) {
            if(back.isInside(x, y) && game.drawnOne == false) { //if they click back button
                action = 0;
            }

                for(int c = 1; c < gameCards.length; c++) { //click face up ikinda messed up so like ya 
                    if(gameCards[c].isInside(x,y)) {
                      
                        game.drawCard(c-1);
                        repaint();
                        
                    }
                }
                if(gameCards[0].isInside(x, y)) {
                
                    game.drawCard(5); //idk
                    
                }

        }
        




    }
    repaint();
}
    }

    public void keyPressed(KeyEvent e)
    {

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            step++;
            if(step == 6)
            {
                currentRoute = new Route(city1, city2, currentSize, tunnel, locomotives);
                routes.add(currentRoute);
                int[][] trackCoords = new int[currentSize][3];
                float totalLength = 0;
                float length1 = 0, length2 = 0;
                int height = 0;
                float normalizedX = city2.getXCoord() - city1.getXCoord();
                float normalizedY = city2.getYCoord() - city1.getYCoord();
                float initialAngle = (float)Math.atan((float)normalizedY/(float)normalizedX);
                float distance = (float)Math.sqrt(normalizedX * normalizedX + normalizedY * normalizedY);
                normalizedX /= distance;
                normalizedY /= distance; 

                normalizedX = 1;
                normalizedY = 1;
                for(int i = 0; i < currentSize/2 + currentSize % 2; i++)
                {
                    float currentLength;
                    float rot;
                    if(i < currentSize/2)
                    {
                        currentLength = (float)Math.pow((2 * (float)(currentSize/2 - i))/(currentSize), 0.5) * (((float)currentSize * Track.WIDTH - distance)/2) - length1;
                        System.out.println(Math.pow((2 * (float)(currentSize/2 - i))/(currentSize), 0.5));
                        rot = (float)(-Math.acos((float)currentLength/Track.WIDTH));
                        length1 += currentLength;
                    } else {
                        currentLength = 0;
                        rot = 0;
                        length1 += 0;
                    }

                    height += (int)((Track.WIDTH * Math.sin(rot)));
                    totalLength = length1 + length2;

                    int x = (int)(totalLength);
                    int y = (int)(height);

                    trackCoords[i][0] = x;
                    trackCoords[i][1] = 300;
                    trackCoords[i][2] = (int)Math.toDegrees(rot);

                    height += (int)((currentSize * Math.sin(rot)));

                }

                for(int i = currentSize - 1; i > currentSize/2; i--)
                {
                    float currentLength = (float)Math.pow((2 * (float)(currentSize/2 - i + currentSize/2 + currentSize % 2))/(currentSize - 1), 0.5) * (((float)currentSize * Track.WIDTH - distance)/2) - length2;
                    System.out.println((((float)currentSize * Track.WIDTH - distance)/2));
                    float rot = (float)(Math.acos((float)currentLength/Track.WIDTH));
                    length2 += currentLength;

                    height += (int)((Track.WIDTH * Math.sin(rot)));
                    totalLength = length1 + length2;

                    int x = (int)(totalLength);
                    int y = (int)(height);

                    trackCoords[i][0] = x ;
                    trackCoords[i][1] = 300;
                    trackCoords[i][2] = (int)Math.toDegrees(rot);

                    height += (int)((currentSize * Math.sin(rot)));
                }
                System.out.println(totalLength + " " + distance);
                currentRoute.makeTracks(trackCoords);
            } else if(step == 7)
            {
                writer.append(city1.getName() + "," + city2.getName() + "," + currentSize + "," + tunnel + "," + locomotives + ",");
                step = 0;
            }
            return;
        }
        if(step == 2)
        {
            Integer s = Integer.parseInt("" + e.getKeyChar());
            if(s != null)
            {
                currentSize = s;
            }
        } else if(step == 3)
        {
            Integer s = Integer.parseInt("" + e.getKeyChar());
            if(s != null)
            {
                color = s;
            }
        } else if(step == 4)
        {
            if(e.getKeyCode() == KeyEvent.VK_1)
            {
                tunnel = true;
            } else if(e.getKeyCode() == KeyEvent.VK_2)
            {
                tunnel = false;
            }
        } else if(step == 5)
        {
            Integer s = Integer.parseInt("" + e.getKeyChar());
            if(s != null)
            {
                locomotives = s;
            }
        }else if(step == 6)
        {
            switch(e.getKeyCode())
            {
            case KeyEvent.VK_RIGHT:
                currentRoute.shiftHorizontal(1);
                break;
            case KeyEvent.VK_LEFT:
            currentRoute.shiftHorizontal(-1);
                break;
            case KeyEvent.VK_UP:
            currentRoute.shiftVertical(-1);
                break;
            case KeyEvent.VK_DOWN:
                currentRoute.shiftVertical(1);
                break;
            }

            if(e.getKeyCode() == KeyEvent.VK_1)
            {
                flipped = true;
            } else if(e.getKeyCode() == KeyEvent.VK_2)
            {
                flipped = false;
            }
        }
        repaint();
    }
    
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}
