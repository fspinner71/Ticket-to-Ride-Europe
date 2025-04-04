import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener, KeyListener {

    private static BufferedImage map, city, deck, ticket;
    public static BufferedImage[] tracks, tunnelTracks, cards, buttons;
    public static BufferedImage bottomBar, leftBar, rightBar, upArrow, errorWindow, notebookPaper, textBoxLarge, textBoxSmall;     
    public static BufferedImage[] stations, owns;
    public static BufferedImage locomotiveTrack, locomotiveTunnelTrack; //extra stuff
    private File cities;
    private FileWriter citiesWriter;
    private ArrayList<TempCity> cityArray;
    private TempCity currentCity;
    private String currentName; 
    private Game game;
    static {
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
   

            //tracks
            tracks = new BufferedImage[9];
            tracks[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueTrack.png"));
            tracks[Game.RED]= ImageIO.read(GamePanel.class.getResource("/Images/RedTrack.png"));
            tracks[Game.ORANGE] = ImageIO.read(GamePanel.class.getResource("/Images/OrangeTrack.png"));
            tracks[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowTrack.png"));
            tracks[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenTrack.png"));
            tracks[Game.PINK] = ImageIO.read(GamePanel.class.getResource("/Images/PinkTrack.png"));
            tracks[Game.WHITE]= ImageIO.read(GamePanel.class.getResource("/Images/WhiteTrack.png"));
            tracks[Game.BLACK] = ImageIO.read(GamePanel.class.getResource("/Images/BlackTrack.png"));
            tracks[Game.ANY] = ImageIO.read(GamePanel.class.getResource("/Images/GrayTrack.png"));

            //tunnel track
            tunnelTracks = new BufferedImage[9];
            tunnelTracks[Game.BLUE] = ImageIO.read(GamePanel.class.getResource("/Images/BlueTunnelTrack.png"));
            tunnelTracks[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedTunnelTrack.png"));
            tunnelTracks[Game.ORANGE] = ImageIO.read(GamePanel.class.getResource("/Images/OrangeTunnelTrack.png"));
            tunnelTracks[Game.YELLOW] = ImageIO.read(GamePanel.class.getResource("/Images/YellowTunnelTrack.png"));
            tunnelTracks[Game.GREEN] = ImageIO.read(GamePanel.class.getResource("/Images/GreenTunnelTrack.png"));
            tunnelTracks[Game.PINK] = ImageIO.read(GamePanel.class.getResource("/Images/PinkTunnelTrack.png"));
            tunnelTracks[Game.WHITE] = ImageIO.read(GamePanel.class.getResource("/Images/WhiteTunnelTrack.png"));
            tunnelTracks[Game.BLACK] = ImageIO.read(GamePanel.class.getResource("/Images/BlackTunnelTrack.png"));
            tunnelTracks[Game.ANY] = ImageIO.read(GamePanel.class.getResource("/Images/GrayTunnelTrack.png"));


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
        cities = new File("csv/cities.csv");
        cityArray = new ArrayList<TempCity>();

        cities = new File("csv/cities.csv");
        game = new Game();
        addKeyListener(this);
        addMouseListener(this);
    }
    public void paint(Graphics g)
    {
        g.drawImage(map, 0, 0, map.getWidth(), map.getHeight(), null);

        for(int i = 0; i < cityArray.size(); i++)
        {
            g.drawImage(city, cityArray.get(i).x - 8, cityArray.get(i).y - 8, 16, 16, null);
            g.drawString(cityArray.get(i).name, cityArray.get(i).x, cityArray.get(i).y);
        }
        if(currentCity != null && currentName != null)
        {
            g.drawImage(city, currentCity.x - 8, currentCity.y - 8, 16, 16, null);
            g.drawString(currentName, currentCity.x, currentCity.y);
        }
    }
    public void mousePressed(MouseEvent e)
    {
        if(currentCity == null)
        {
            currentCity = new TempCity();
            currentCity.x = e.getX();
            currentCity.y = e.getY();
            currentName = "";
        }
        repaint();
    }
    public void keyPressed(KeyEvent e)
    {
        System.out.println("hi");
        if(currentCity == null)
        {
            return;
        }
        if(e.getKeyCode() == e.VK_BACK_SPACE)
        {
            if(currentName.length() >= 1)
            {
                String nextName = currentName.substring(0, currentName.length() - 1);
                System.out.println("FROM: " + currentName + "\nTO:" + nextName + "\n");
                currentName = nextName;
                System.out.println(currentName);
            }
        } else if(e.getKeyCode() == e.VK_ENTER)
        {
            currentCity.name = currentName;
            cityArray.add(currentCity);

            try {
                citiesWriter = new FileWriter("csv/cities.csv");
            } catch (Exception er) {
                System.out.println("Could not write city");
            }

            currentCity = null;
            currentName = "";
        }
        repaint();
    }
    public void keyTyped(KeyEvent e)
    {
        if(currentCity != null && e.getKeyChar() != '')
        {
            currentName += e.getKeyChar();
        }
        repaint();
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void keyReleased(KeyEvent e){}

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}
