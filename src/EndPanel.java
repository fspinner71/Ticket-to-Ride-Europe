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

    private static BufferedImage map, city;
    private File cities;
    private FileWriter citiesWriter;
    private City currentCity;
    private String currentName;
    static {
        try
        {
            map = ImageIO.read(EndPanel.class.getResource("/Images/Map.png"));
            city = ImageIO.read(EndPanel.class.getResource("/Images/City.png"));
        } catch (Exception e)
        {
            System.out.println("Failed to load EndPanel images");
        }
    }

    public EndPanel()
    {

    }
    public void paint(Graphics g)
    {
        g.drawImage(map, getWidth()*1/2, 0, map.getWidth()*2/3, map.getHeight()*4/5, null);

        
    }
}
