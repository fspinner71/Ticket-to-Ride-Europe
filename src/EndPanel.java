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

public class EndPanel extends JPanel{
    private static final int MAP_X = 625;
    private static BufferedImage bg;
    private static Font font;
    private static Font hugeFont;
    private boolean[] europeanExpress;
    private int[] rankings = {0, 1, 2, 3};;

    private Game game;

    static
    {
        font = new Font("Comic Sans MS", Font.BOLD, 32);
        hugeFont = new Font("Comic Sans MS", Font.BOLD, 70);
        try
        {
            bg = ImageIO.read(EndPanel.class.getResource("/Images/NotebookPaper.png"));
        } catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public EndPanel(Game game)
    {
        this.game = game;

        europeanExpress = new boolean[4];

        int lengthToBeat = 0;
        int[] lengths = new int[4];
        for(int i = 0; i < 4; i++)
        {
            Player p = game.getPlayers()[i];
            
            for(Ticket t : p.getTickets())
            {
                if(p.findRoute(t.getCities()[0].getName(), null, t.getCities()[1].getName()))
                {
                    p.addPoints(t.getPoints());
                } else {
                    p.addPoints(-t.getPoints());
                }
            }

            int longestPath = p.initLongestPath();
            System.out.println(longestPath);
            lengths[i] = longestPath;

            if(longestPath > lengthToBeat)
            {
                lengthToBeat = longestPath;
            }
        }

        for(int i = 0; i < 4; i++)
        {
            if(lengths[i] >= lengthToBeat)
            {
                game.getPlayers()[i].addPoints(10);
                europeanExpress[i] = true;
            }
        }

        for(int i = 0; i < 3; i++)
        {
            int largest = -9999;
            int largestIndex = i;
            System.out.println("CHECKING PLAYER " + rankings[i]);
            for(int j = i + 1; j < 4; j++)
            {
                if(game.getPlayers()[rankings[j]].getPoints() > largest)
                {
                    largest = game.getPlayers()[rankings[j]].getPoints();
                    largestIndex = j;
                }
            }
            System.out.println("LARGEST SCORE AND INDEX " + largest + " " + largestIndex);
            System.out.println("SCORE: " + game.getPlayers()[rankings[i]].getPoints());
            if(largest > game.getPlayers()[rankings[i]].getPoints())
            {
                System.out.println("SWITCHING " + rankings[i] + " AND " + rankings[largestIndex]);
                int temp = rankings[i];
                rankings[i] = rankings[largestIndex];
                rankings[largestIndex] = temp;
            }
        }
        for(int i = 0; i < 4; i++)
        {
            System.out.println(rankings[i]);
        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(bg, 0, 0, bg.getWidth(), bg.getHeight(),null);
        g.drawImage(GamePanel.map, getWidth() - GamePanel.map.getWidth(), 0, GamePanel.map.getWidth(), GamePanel.map.getHeight(), null);

        int diff = 260;

        for(int i = 0; i < 4; i++)
        {
            Player p = game.getPlayers()[rankings[i]];
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Player " + (rankings[i]+1) + " - " + p.getPoints(), 5, 35 + diff * i);

            int stationOffset = 60;
            for(int j = 0; j < p.getNumStations(); j++)
            {
                g.drawImage(GamePanel.stations[i], 5 + stationOffset * j, 40 + diff * i, 50, 50, null);
            }

            if(europeanExpress[rankings[i]])
            {
                g.setFont(font);
                g.setColor(Color.RED);
                g.drawString("European", 200, 60 + diff * i);
                g.drawString("Express", 210, 80 + diff * i);
            }

            int ticketOffset = 110;
            ArrayList<Ticket> tickets = p.getTickets();
            for(int j = 0; j < tickets.size(); j++)
            {
                Ticket t = tickets.get(j);
                t.setX(5 + ticketOffset * j);
                t.setY(110 + diff * i);
                t.paintMini(g);

                if(!p.findRoute(t.getCities()[0].getName(), null, t.getCities()[1].getName()))
                {
                    g.setFont(hugeFont);
                    g.setColor(Color.RED);

                    g.drawString("X", 30 + ticketOffset * j, 167 + diff * i);
                }
            }
        }

        Graphics2D g2 = (Graphics2D)g;

        g2.translate(MAP_X - GamePanel.MAP_X - 15, 0);
        for (Route r : game.getRoutes()) {

            r.paintOffset(g2, MAP_X, 0);
        }
        ArrayList<City> cities = game.getCities();
        for (int i = 0; i < cities.size(); i++) {
            cities.get(i).paintOffset(g2, MAP_X - GamePanel.MAP_X-15, 0);
        }
        for (City a : game.getCities()) {
            if (a.hasStation()) {
                g2.drawImage(GamePanel.stations[0], GamePanel.MAP_X + a.getXCoord() - 25 / 2 - 7,
                        GamePanel.MAP_Y + a.getYCoord() - 25 / 2 - 12, GamePanel.stations[0].getWidth() / 50,
                        GamePanel.stations[0].getHeight() / 50, null);
            }
        }

        g.setFont(hugeFont);
        g.setColor(Color.RED);

        g.drawString("Player " + (rankings[0] + 1) + " wins!!", 500, 1000);
    }
}
