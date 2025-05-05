
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Ticket {
    public static final int WIDTH = 250;
    public static final int HEIGHT = 165;

    public static final int MINI_WIDTH = 100;
    public static final int MINI_HEIGHT = 66;

    private static BufferedImage image;
    private static Font font;
    private static Font miniFont;

    private City[] cities;
    private int points;
    private int x = 0, y = 0;

    static {

        font = new Font("Comic Sans MS", Font.BOLD, 24);
        miniFont = new Font("Comic Sans MS", Font.BOLD, 10);
        try {
            image = ImageIO.read(Ticket.class.getResource("/Images/Ticket.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Ticket(City one, City two, int p) {
        cities = new City[2];
        setCities(one, two);
        points = p;

    }

    public City[] getCities() {
        return cities;
    }

    public int getPoints() {
        return points;
    }

    public void setCities(City a, City b) {
        cities[0] = a;
        cities[1] = b;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);

        int startX = (int)(WIDTH * 0.21);
        int startY = (int)(HEIGHT * 0.215);
        int mapWidth = (int)(WIDTH * 0.55);
        int mapHeight = (int)(HEIGHT * 0.56);

        int x1 = x + startX + (int)(((float)mapWidth/GamePanel.MAP_WIDTH) * cities[0].getXCoord());
        int y1 = y + startY + (int)(((float)mapHeight/GamePanel.MAP_HEIGHT) * cities[0].getYCoord());
        
        int x2 = x + startX + (int)(((float)mapWidth/GamePanel.MAP_WIDTH) * cities[1].getXCoord());
        int y2 = y + startY + (int)(((float)mapHeight/GamePanel.MAP_HEIGHT) * cities[1].getYCoord());

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);

        g2.drawLine(x1, y1, x2, y2);

        int citySize = 10;

        g.drawImage(City.image, x1 - citySize/2, y1 - citySize/2, citySize, citySize, null);
        g.drawImage(City.image, x2 - citySize/2, y2 - citySize/2, citySize, citySize, null);

        g.setFont(font);
        g.setColor(Color.RED);

        GamePanel.drawCenteredString(g2, cities[0].getName(), x, y, WIDTH, startY, font);
        GamePanel.drawCenteredString(g2, cities[1].getName(), x, y + mapHeight + startY, WIDTH, startY, font);
        GamePanel.drawCenteredString(g2, "" + points, x, y, startX, HEIGHT, font);
        GamePanel.drawCenteredString(g2, "" + points, x + startX + mapWidth, y, startX, HEIGHT, font);
    }

    public void paintMini(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(image, x, y, MINI_WIDTH, MINI_HEIGHT, null);

        int startX = (int)(MINI_WIDTH * 0.21);
        int startY = (int)(MINI_HEIGHT * 0.215);
        int mapWidth = (int)(MINI_WIDTH * 0.55);
        int mapHeight = (int)(MINI_HEIGHT * 0.56);

        int x1 = x + startX + (int)(((float)mapWidth/GamePanel.MAP_WIDTH) * cities[0].getXCoord());
        int y1 = y + startY + (int)(((float)mapHeight/GamePanel.MAP_HEIGHT) * cities[0].getYCoord());
        
        int x2 = x + startX + (int)(((float)mapWidth/GamePanel.MAP_WIDTH) * cities[1].getXCoord());
        int y2 = y + startY + (int)(((float)mapHeight/GamePanel.MAP_HEIGHT) * cities[1].getYCoord());

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);

        g2.drawLine(x1, y1, x2, y2);

        int citySize = 5;

        g.drawImage(City.image, x1 - citySize/2, y1 - citySize/2, citySize, citySize, null);
        g.drawImage(City.image, x2 - citySize/2, y2 - citySize/2, citySize, citySize, null);

        g.setFont(miniFont);
        g.setColor(Color.RED);

        GamePanel.drawCenteredString(g2, cities[0].getName(), x, y, MINI_WIDTH, startY, miniFont);
        GamePanel.drawCenteredString(g2, cities[1].getName(), x, y + mapHeight + startY, MINI_WIDTH, startY, miniFont);
        GamePanel.drawCenteredString(g2, "" + points, x, y, startX, MINI_HEIGHT, miniFont);
        GamePanel.drawCenteredString(g2, "" + points, x + startX + mapWidth, y, startX, MINI_HEIGHT, miniFont);
    }
}
