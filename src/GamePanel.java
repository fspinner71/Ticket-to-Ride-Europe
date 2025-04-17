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
    public static BufferedImage bottomBar, leftBar, rightBar, upArrow, errorWindow, notebookPaper, textBoxLarge, textBoxSmall;     
    public static BufferedImage[] stations, owns;
    public static BufferedImage locomotiveTrack, locomotiveTunnelTrack; //extra stuff 
    public static final int MAP_X = 305;
    public static final int MAP_Y = -10;
    private static Font font;
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
            

        } catch (Exception e)
        {
            System.out.println("Failed to load GamePanel images");
        }
    }

    public GamePanel()
    {
        game = new Game();
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
        g.drawImage(map, MAP_X, MAP_Y, map.getWidth(), map.getHeight(), null);
        g.drawImage(bottomBar, getWidth()/2 - bottomBar.getWidth()/2, getHeight() - bottomBar.getHeight(), bottomBar.getWidth(), bottomBar.getHeight(), null);
        g.drawImage(leftBar, 0, 0, leftBar.getWidth(), leftBar.getHeight(),null);
        g.drawImage(rightBar, getWidth() - rightBar.getWidth(), 0, rightBar.getWidth(), rightBar.getHeight(),null);

        ArrayList<City> cities = game.getCities();
        for(int i = 0; i < cities.size(); i++)
        {
            cities.get(i).paint(g);
        }

        for(Route r : routes)
        {
            r.paint(g);
        }

        String text = "";
        switch(step)
        {
        case 0:
            text = "Pick city 1";
            break;
        case 1:
            text = "Pick city 2";
            break;
        case 2:
            text = "Pick length";
            break;
        case 3:
            text = "Pick color\n0=red, 1=blue, 2=yellow, 3=green, 4=orange, 5=pink, 6=white, 7=black, 8=any";
            break;
        case 4:
            text = "Pick tunnel\n 1 = true, 2 = false";
            break;
        case 5:
            text = "Pick number of locomotives";
            break;
        case 6:
            text = "Shift tunne;";
            break;
        }
        g.setFont(font);
        g.drawString(text, 30,900);
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            int x = e.getX();
            int y = e.getY();
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
        }
        repaint();
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
