import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener {

    private static BufferedImage map;
    private File cities;
    private FileWriter citiesWriter;
    static {
        try
        {
            System.out.println("/Images/Map.png");
            map = ImageIO.read(GamePanel.class.getResource("/Images/Map.png"));
        } catch (Exception e)
        {
            System.out.println("Failed to load GamePanel images");
        }
    }

    public GamePanel()
    {
        cities = new File("csv/cities.csv");
        addMouseListener(this);
    }
    public void paint(Graphics g)
    {
        g.drawImage(map, 0, 0, map.getWidth(), map.getHeight(), null);
    }
    public void mousePressed(MouseEvent e)
    {
        try
        {
            citiesWriter = new FileWriter("csv/cities.csv");
        } catch(Exception ex)
        {

        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
