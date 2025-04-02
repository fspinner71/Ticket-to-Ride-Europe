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
        cities = new File("csv/cities.csv");
        cityArray = new ArrayList<TempCity>();
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
            currentCity = null;
            currentName = "";
        }
        repaint();
    }
    public void keyTyped(KeyEvent e)
    {
        if(currentCity != null)
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
