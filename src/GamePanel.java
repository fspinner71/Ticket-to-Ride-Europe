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
    public static BufferedImage bottomBar, leftBar, rightBar, upArrow, downArrow, errorWindow, notebookPaper, textBoxLarge, textBoxSmall;     
    private Button[] actions, gameCards;
    public static BufferedImage[] stations, owns;
    public static BufferedImage locomotiveTrack, locomotiveTunnelTrack; //extra stuff 
    public static final int MAP_X = 305;
    public static final int MAP_Y = -10;
    private static Font font, bigFont;

    private Button back, okButton, endTurnButton, confirm, arrowup, arrowdown, arrowup2, arrowdown2;
    private int action, numberoflocomotivestheywanttouse, routebuyingcolor; // 0 = hasnt picjed, 1 = draw, 2 = route, 3 = station, 4 = ticket
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
    private Route currentlyBuying;
    private int selectedTrack;
    private String[] arrayforchoosingroutecolor;
    static {
        font = new Font("Comic Sans MS", Font.BOLD, 24);
        bigFont = new Font("Comic Sans MS", Font.BOLD, 36);
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
            cards[Game.RED] = ImageIO.read(GamePanel.class.getResource("/Images/RedCard.png"));
            cards[Game.ANY] = ImageIO.read(GamePanel.class.getResource("/Images/Locomotive.png"));

            //gui stuf f
            bottomBar = ImageIO.read(GamePanel.class.getResource("/Images/BottomBar.png"));
            leftBar = ImageIO.read(GamePanel.class.getResource("/Images/LeftBar.png"));
            rightBar = ImageIO.read(GamePanel.class.getResource("/Images/RightBar.png"));
            upArrow = ImageIO.read(GamePanel.class.getResource("/Images/UpArrow.png"));
            downArrow = ImageIO.read(GamePanel.class.getResource("/Images/DownArrow.png"));
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
        actions = new Button[4];
        gameCards = new Button[6]; //like when you draw cards wtv it has includes the face down card ;; 0 = facedown, 1-5 = cards

        gameCards[0] = new Button(1645, 115 , cards[0].getWidth()/5, cards[0].getHeight()/5, deck); //deck button
        for(int c = 1; c < gameCards.length; c++) {
            gameCards[c] = new Button(1645, 115 + c * 135, cards[0].getWidth()/5, cards[0].getHeight()/5, deck); //face up cards button image is default
                }
        actions[0] = new Button(1600, 300-10, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.BLUE]);
        actions[1] = new Button(1600, 410-10, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.YELLOW]);
        actions[2] = new Button(1600, 520-10, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.PINK]);
        actions[3] = new Button(1600, 630-10, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.ORANGE]);
            back = new Button(1600, 925, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.RED]);
            okButton = new Button(820, 620, buttons[0].getWidth()/3, buttons[0].getHeight()/3, buttons[Game.GREEN]);
            endTurnButton = new Button(1600, 450, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.RED]);
            confirm = new Button(1600, 820,buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.GREEN]);
            arrowup = new Button(1700, 500,upArrow.getWidth()/4, upArrow.getHeight()/4, upArrow); 
            arrowdown = new Button(1700, 600,upArrow.getWidth()/4, upArrow.getHeight()/4, downArrow);
            arrowup2 = new Button(1700, 660,upArrow.getWidth()/4, upArrow.getHeight()/4, upArrow); 
            arrowdown2 = new Button(1700, 760,upArrow.getWidth()/4, upArrow.getHeight()/4, downArrow);
        try {
            writer = new PrintWriter("routes.csv", "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }

        routes = new ArrayList<Route>();
        action = 0;
        numberoflocomotivestheywanttouse= 0;
        routebuyingcolor = 0;
         String[] temp = {"Red", "Blue", "Yellow", "Green", "Orange", "Pink", "White", "Black" };
         arrayforchoosingroutecolor = temp;
        currentlyBuying = null;
    }
    @Override
    public void paint(Graphics g)
    {
        if(game.turnended) { //if turn just ended u need to reset the screen yk
            action = -1;
           System.out.println("end of turn");
        }
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(font);
        g2.setFont(font);
        if(game.errorPanel == true) {
           
            g.drawImage(errorWindow, 725, 300, errorWindow.getWidth()/3, errorWindow.getHeight()/3, null);
            okButton.paint(g);

            if(game.errorMessage.equals("You can't draw a locomotive!")) {
            g2.drawString(game.errorMessage, 759, 510);
            g2.drawString("OK", 904, 661);
            }
           
        }
        else {
        g.drawImage(map, MAP_X, MAP_Y, map.getWidth(), map.getHeight(), null);
        g.drawImage(bottomBar, getWidth()/2 - bottomBar.getWidth()/2, getHeight() - bottomBar.getHeight(), bottomBar.getWidth(), bottomBar.getHeight(), null);
        g.drawImage(leftBar, 0, 0, leftBar.getWidth(), leftBar.getHeight(),null);
        g.drawImage(rightBar, getWidth() - rightBar.getWidth(), 0, rightBar.getWidth(), rightBar.getHeight(),null);
        g.drawImage(textBoxLarge, (bottomBar.getWidth())*5/9 + leftBar.getWidth(), getHeight() - bottomBar.getHeight() + 100, 130, 150, null);
        g.drawImage(textBoxLarge, (bottomBar.getWidth())*6/9 + leftBar.getWidth(), getHeight() - bottomBar.getHeight() + 100, 130, 150, null);
        g.drawImage(textBoxLarge, (bottomBar.getWidth())*7/9 + leftBar.getWidth(), getHeight() - bottomBar.getHeight() + 100, 130, 150, null);
        g2.drawString("Stations", bottomBar.getWidth()*5/9 + leftBar.getWidth() + 10, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString("Points", bottomBar.getWidth()*6/9 + leftBar.getWidth() + 15, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString("Cars", bottomBar.getWidth()*7/9 + leftBar.getWidth() + 20, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString("" + game.players[game.turn].getNumStations(), bottomBar.getWidth()*5/9 + leftBar.getWidth() + 10, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString("Points", bottomBar.getWidth()*6/9 + leftBar.getWidth() + 15, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString("Cars", bottomBar.getWidth()*7/9 + leftBar.getWidth() + 20, getHeight() - bottomBar.getHeight() + 85);
        g2.drawString(Integer.toString(game.turn), 100, 200);

            if(action == -1) { //-1 is if the turn ended and itll juust show the end turn button
            endTurnButton.paint(g);
            g2.drawString("END TURN", 1682, 508);

        }
            if(action == 0) { //0 is like the action screen and the start of their turn
        for(Button a : actions) { 
            a.paint(g);
        }
        numberoflocomotivestheywanttouse = 0;
        routebuyingcolor = 0;
        currentlyBuying = null;

        System.out.println("start of turn");
        g2.setFont(bigFont);
        g2.drawString("DRAW", 1695, 354);
        g2.drawString("ROUTE", 1688, 464);
        g2.drawString("STATION", 1664, 573);
        g2.drawString("TICKET", 1684, 684);
        g2.setFont(font);
    }
            if(action == 1) { //1 is if they decide to draw cards
        for(int c = 1; c < gameCards.length; c++) { //paint faceup
            int[] faceup = game.getFaceUpCards();
         
            gameCards[c].setImage(cards[faceup[c-1]]);
            gameCards[c].paint(g);
        }
        if(game.getDeck().isEmpty() == false) { //paint deck
          gameCards[0].paint(g);
           
        }

        back.paint(g);
        g2.setFont(bigFont);
        g2.drawString("BACK", 1705, 987);
        g2.drawString("Draw", 1703, 59);
        g2.drawString("Cards", 1698, 100);
        g2.setFont(font);
    }
        if(action == 2) { //2 is if they decide to buy a route
        

            back.paint(g);
            confirm.paint(g);
            g2.setFont(bigFont);
            arrowup.paint(g);
            arrowdown.paint(g);
            arrowup2.paint(g);
            arrowdown2.paint(g);
        g2.drawString("BACK", 1705, 987);
        g2.drawString("CONFIRM", 1664, 883);
        g2.drawString("Purchasing", 1657, 59);
        g2.drawString("Route", 1700, 105);
        g2.drawString("Select", 1690, 380);
        g2.drawString("number of", 1660, 415);
        g2.drawString("locomotives and", 1615, 450);
        g2.drawString("buying color", 1645, 485);
        g2.drawString(String.valueOf(numberoflocomotivestheywanttouse), 1740, 748);
        g2.drawString(arrayforchoosingroutecolor[routebuyingcolor], 1700, 585);
        g2.drawString("to", 1730, 240);
            if(currentlyBuying != null) {
                g2.drawString(currentlyBuying.getA().getName(), 1730, 200);
                g2.drawString(currentlyBuying.getB().getName(), 1730, 300);
            }
        g2.setFont(font);
    }

        ArrayList<City> cities = game.getCities();
        for(int i = 0; i < cities.size(); i++)
        {
            cities.get(i).paint(g);
            if(city1 != null)
            {
                if(cities.get(i).getName() == city1.getName())
                {
                    g.setColor(Color.RED);
                    g.setFont(font);
                    g.drawString("X", cities.get(i).getXCoord() + MAP_X, cities.get(i).getYCoord() + MAP_Y);
                }
            }

            if(city2 != null)
            {
                if(cities.get(i).getName() == city2.getName())
                {
                    g.setColor(Color.RED);
                    g.setFont(font);
                    g.drawString("X", cities.get(i).getXCoord() + MAP_X, cities.get(i).getYCoord() + MAP_Y);
                }
            }
        }

        for(Route r : game.getRoutes())
        {
            r.paint(g);
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
            text = "Pick color: 0=red, 1=blue, 2=yellow, 3=green, 4=orange, 5=pink, 6=white, 7=black, 8=any";
            break;
        case 4:
            text = "Pick tunnel\n 1 = true, 2 = false";
            break;
        case 5:
            text = "Pick number of locomotives";
            break;
        case 6:
            text = "Shift tunnel;";
            break;
        }
        g.setFont(font);
        g.drawString(text, 30,900);
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

            if(action == -1) {
                if(endTurnButton.isInside(x, y)) {
                    game.turnended = false;
                    action = 0;
                   System.out.println("they pressed the end turn button" + action);
                   repaint();
                   return;
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
        else if(action == 1) { //if they decide to draw card
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
        else if(action == 2) { //f tjey decide to buy a route
            if(back.isInside(x, y)) { //if they click back button
                action = 0;
            }
            if(arrowup2.isInside(x, y)) {
                numberoflocomotivestheywanttouse++;
            }
            if(arrowdown2.isInside(x, y) && numberoflocomotivestheywanttouse > 0) {
                numberoflocomotivestheywanttouse--;
            }
            if(arrowdown.isInside(x, y)) {
                if(routebuyingcolor == 0) {
                    routebuyingcolor = 7;
                }
                else {
                    routebuyingcolor--;
                }
                
            }
            if(arrowup.isInside(x, y)) {
                if(routebuyingcolor == 7) {
                    routebuyingcolor = 0;
                }
                else {
                    routebuyingcolor++;
                }
                
            }


            for(int c =0; c< game.getRoutes().size(); c++) {
                for(Button a: game.getRoutes().get(c).getButtons()) {
                    if(a.isInside(x, y)) {
                        currentlyBuying = game.getRoutes().get(c);
                        break;
                    }
                } {

                }
            }
            
        }
        if(confirm.isInside(x, y)) { //biu route
            game.buyRoute(currentlyBuying, numberoflocomotivestheywanttouse, routebuyingcolor);
        }
        if(game.turnended) { //if turn just ended u need to reset the screen yk
            action = -1;
           System.out.println("end of turn");
        }

        
        }
    }
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == e.VK_ESCAPE)
        {
            try {
                
                writer.close();
            } catch (Exception er) {
                System.out.println(er);
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            step++;
            if(step == 6)
            {
                currentRoute = new Route(city1, city2, currentSize, tunnel, locomotives, color);
                routes.add(currentRoute);
                int[][] trackCoords = new int[currentSize][3];

                int Xc = city2.getXCoord() - city1.getXCoord();
                int Yc = city2.getYCoord() - city1.getYCoord();


                for(int i = 0; i < currentSize; i++)
                {
                    trackCoords[i][0] = city1.getXCoord() + MAP_X;
                    trackCoords[i][1] = city1.getYCoord() + MAP_Y;
                    trackCoords[i][2] = (int)Math.toDegrees(Math.atan((float)Yc/(float)Xc));
                }
                
                currentRoute.makeTracks(trackCoords);
            } else if(step == 7)
            {
                try 
                {
                    writer.append(city1.getName() + "," + city2.getName() + "," + color + "," + tunnel + "," + locomotives + "," + currentSize + "," + currentRoute.toString());
                } catch(Exception er)
                {
                    System.out.println(er);
                }
                step = 0;
                selectedTrack = 0;
            }
            repaint();
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
            int amt = 1;
            if(e.isShiftDown())
            {
                amt *= 10;
            }
            switch(e.getKeyCode())
            {
            case KeyEvent.VK_RIGHT:
                currentRoute.moveTrack(selectedTrack, amt, 0, 0);
                break;
            case KeyEvent.VK_LEFT:
                currentRoute.moveTrack(selectedTrack, -amt, 0, 0);
                break;
            case KeyEvent.VK_UP:
                currentRoute.moveTrack(selectedTrack, 0, -amt, 0);
                break;
            case KeyEvent.VK_DOWN:
                currentRoute.moveTrack(selectedTrack, 0, amt, 0);
                break;
            case KeyEvent.VK_E:
                currentRoute.moveTrack(selectedTrack, 0, 0, -amt);
                break;
            case KeyEvent.VK_Q:
                currentRoute.moveTrack(selectedTrack, 0, 0, amt);
                break;
            }

            if(e.getKeyCode() == KeyEvent.VK_1)
            {
                selectedTrack -= 1;
            } else if(e.getKeyCode() == KeyEvent.VK_2)
            {
                selectedTrack += 1;
            }
            repaint();
        }
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