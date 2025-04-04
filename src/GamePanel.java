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

    private static BufferedImage map, city;
    private File cities;
    private FileWriter citiesWriter;
    private ArrayList<TempCity> cityArray;
    private TempCity currentCity;
    private String currentName;
    private Track t;
    static {
        try
        {
            map = ImageIO.read(GamePanel.class.getResource("/Images/Map.png"));
            city = ImageIO.read(GamePanel.class.getResource("/Images/City.png"));
        } catch (Exception e)
        {
            System.out.println("Failed to load GamePanel images");
        }
    }

    public GamePanel()
    {

        t = new Track(0, 500, 300, 0);
        cityArray = new ArrayList<TempCity>();

        cities = new File("c:\\Users\\k1210611\\Downloads\\TTREurope\\Ticket-to-Ride-Europe\\src\\csv\\cities.csv");
        addKeyListener(this);
        addMouseListener(this);

        try {
            citiesWriter = new FileWriter("c:\\Users\\k1210611\\Downloads\\TTREurope\\Ticket-to-Ride-Europe\\src\\csv\\cities.csv");
        } catch (Exception er) {
            System.out.println(er);
        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
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
        t.paint((Graphics2D)g);
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            if(currentCity == null)
            {
                currentCity = new TempCity();
                currentCity.x = e.getX();
                currentCity.y = e.getY();
                currentName = "";
            }
        } else if(e.getButton() == MouseEvent.BUTTON3)
        {
            t.rotate((float)Math.toRadians(5));
            System.out.println(t.getRotation());
        }
        repaint();
    }
    public void keyPressed(KeyEvent e)
    {

        if(e.getKeyCode() == e.VK_ESCAPE)
        {
            try{
                citiesWriter.flush();
                citiesWriter.close();
                return;
            } catch(Exception sfsdf)
            {
                System.out.println(e);
            }
        }
        if(currentCity == null)
        {
            return;
        }
        if(e.getKeyCode() == e.VK_BACK_SPACE)
        {
            if(currentName.length() >= 1)
            {
                String nextName = currentName.substring(0, currentName.length() - 1);
                currentName = nextName;
                System.out.println(currentName);
            }
        } else if(e.getKeyCode() == e.VK_ENTER)
        {
            currentCity.name = currentName;
            cityArray.add(currentCity);

            try {
                citiesWriter.append(currentCity.name + "," + currentCity.x + "," + currentCity.y + ",");
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
