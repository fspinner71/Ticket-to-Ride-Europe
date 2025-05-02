import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener {

    private static BufferedImage map, city, deck, ticket;
    public static BufferedImage[] cards, buttons;
    public static BufferedImage bottomBar, leftBar, rightBar, upArrow, downArrow, errorWindow, notebookPaper, textBoxLarge, textBoxSmall;     
    private Button[] actions, gameCards;
    public static BufferedImage[] stations, owns;
    public static BufferedImage locomotiveTrack, locomotiveTunnelTrack; //extra stuff 
    public static final int MAP_X = 305;
    public static final int MAP_Y = -10;
    public static final int MAP_WIDTH = 1295;
    public static final int MAP_HEIGHT = 824;
    private static Font font, bigFont;

    private Button back, okButton, endTurnButton, confirm, arrowup, arrowdown, arrowup2, arrowdown2, acceptButton, declineButton;
    private int action, numberoflocomotivestheywanttouse, routebuyingcolor, extraCardsNeeded; // 0 = hasnt picjed, 1 = draw, 2 = route, 3 = station, 4 = ticket
    private Game game;
    private City selected;
    private PrintWriter writer;
    private boolean buyingTunnel;

    private City theywannaplacestationon;
    private Route currentlyBuying;
    private int selectedTrack;
    private String[] arrayforchoosingroutecolor;

    //plr 1 blue, plr 2 red, plr 3 green, plr 4 yellow

    private int[] threecards;

    static {
        font = new Font("Comic Sans MS", Font.BOLD, 24);
        bigFont = new Font("Comic Sans MS", Font.BOLD, 36);
        try {
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


    public GamePanel() {
        game = new Game();
        addMouseListener(this);
        actions = new Button[4];
        gameCards = new Button[6]; //like when you draw cards wtv it has includes the face down card ;; 0 = facedown, 1-5 = cards

        gameCards[0] = new Button(1645, 115, cards[0].getWidth() / 5, cards[0].getHeight() / 5, deck); //deck button
        for (int c = 1; c < gameCards.length; c++) {
            gameCards[c] = new Button(1645, 115 + c * 135, cards[0].getWidth() / 5, cards[0].getHeight() / 5, deck); //face up cards button image is default
        }
        actions[0] = new Button(1600, 300 - 10, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.BLUE]);
        actions[1] = new Button(1600, 410 - 10, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.YELLOW]);
        actions[2] = new Button(1600, 520 - 10, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.PINK]);
        actions[3] = new Button(1600, 630 - 10, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.ORANGE]);
        back = new Button(1600, 925, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.RED]);
        okButton = new Button(820, 620, buttons[0].getWidth() / 3, buttons[0].getHeight() / 3, buttons[Game.GREEN]);
        endTurnButton = new Button(1600, 450, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.RED]);
        confirm = new Button(1600, 820, buttons[0].getWidth() / 2, buttons[0].getHeight() / 2, buttons[Game.GREEN]);
        arrowup = new Button(1700, 500, upArrow.getWidth() / 4, upArrow.getHeight() / 4, upArrow); 
        arrowdown = new Button(1700, 600, upArrow.getWidth() / 4, upArrow.getHeight() / 4, downArrow);
        arrowup2 = new Button(1700, 660, upArrow.getWidth() / 4, upArrow.getHeight() / 4, upArrow); 
        arrowdown2 = new Button(1700, 760, upArrow.getWidth() / 4, upArrow.getHeight() / 4, downArrow);
            acceptButton =new Button(595, 600, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.GREEN]);
            declineButton =new Button(995, 600, buttons[0].getWidth()/2, buttons[0].getHeight()/2, buttons[Game.RED]);
        action = 0;
        numberoflocomotivestheywanttouse = 0;
        routebuyingcolor = 0;
        String[] temp = {"Red", "Blue", "Yellow", "Green", "Orange", "Pink", "White", "Black", "Locomotive"};
        arrayforchoosingroutecolor = temp;
        currentlyBuying = null;
        theywannaplacestationon = null;
        buyingTunnel = false; 
    }

    @Override
    public void paint(Graphics g) {
        if (game.turnended) { //if turn just ended u need to reset the screen yk
            action = -1;
            System.out.println("end of turn");
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);

        //28 
        if (game.errorPanel == true) {

            g.drawImage(errorWindow, 725, 300, errorWindow.getWidth() / 3, errorWindow.getHeight() / 3, null);
            okButton.paint(g);
            System.out.println(game.errorMessage);
          
            g2.drawString(game.errorMessage, 759 - (game.errorMessage.length() - 28) * 6, 510);
            g2.drawString("OK", 904, 661);
            
           
            
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
        g2.drawString("" + game.players[game.turn].getNumStations(), bottomBar.getWidth()*5/9 + leftBar.getWidth() + 50, getHeight() - bottomBar.getHeight() + 180);
        g2.drawString("" + game.players[game.turn].getPoints(), bottomBar.getWidth()*6/9 + leftBar.getWidth() + 50, getHeight() - bottomBar.getHeight() + 180);
        g2.drawString("" + game.players[game.turn].getNumTrains(), bottomBar.getWidth()*7/9 + leftBar.getWidth() + 50, getHeight() - bottomBar.getHeight() + 180);
        
        Player[] players = game.getPlayers();
        for(int i = 0; i < players.length; i++)
        {
            Player p = players[i];
            g.setFont(bigFont);
            g.setColor(Color.BLACK);
            g.drawString("Player " + (i + 1),5, 35 + 260 * i);
            g.setFont(font);
            g.drawImage(textBoxSmall, 15, 70 + 260*i, 125, 50, null);
            g.drawString("Cards", 45, 70 + 260*i);
            g.drawString("" + p.getTrainCards().length, 70, 105 + 260*i);

            g.drawImage(textBoxSmall, 150, 70 + 260*i, 125, 50, null);
            g.drawString("Stations", 170, 70 + 260*i);
            g.drawString("" + p.getNumStations(), 205, 105 + 260*i);

            g.drawImage(textBoxSmall, 15, 140 + 260*i, 125, 50, null);
            g.drawString("Tickets", 35, 140 + 260*i);
            g.drawString("" + p.getTickets().size(), 70, 175 + 260*i);

            g.drawImage(textBoxSmall, 150, 140 + 260*i, 125, 50, null);
            g.drawString("Cars", 185, 140 + 260*i);
            g.drawString("" + p.getNumTrains(), 195, 175 + 260*i);

            g.drawImage(textBoxSmall, 85, 210 + 260*i, 125, 50, null);
            g.drawString("Score", 110, 210 + 260*i);
            g.drawString("" + p.getPoints(), 125, 245 + 260*i);
        }
        //draw player x
        g2.setFont(bigFont);
            String currentplayer = "Player " + (game.turn+1);
        g2.drawString(currentplayer, 325, 822);
        g2.setFont(font);
       
        //draw current player's cards
        int totalnum = game.getPlayers()[game.turn].getNumTrainCards();
        int imagegap = 0;
        if(totalnum !=  0) {
             imagegap = 400/totalnum;
        }
        if(totalnum < 10) {
            imagegap = 40;
        }
        int tempimagegapthing = 0;
        for(int c = 0; c < game.getPlayers()[game.turn].getTrainCards().length; c++) {
            for(int i = 0; i  < game.getPlayers()[game.turn].getTrainCards()[c]; i++) { //325 900
                g.drawImage(rotatecounterclockwise(cards[c]), 325 + tempimagegapthing * imagegap, 830, rotatecounterclockwise(cards[c]).getWidth()/5, rotatecounterclockwise(cards[c]).getHeight()/5, null);
                tempimagegapthing++;
            }
        }

        g.drawString("action: " + action, 0, 0);
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
        theywannaplacestationon = null;
        buyingTunnel = false;
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
        g2.drawString(arrayforchoosingroutecolor[routebuyingcolor], 1715 - (arrayforchoosingroutecolor[routebuyingcolor].length() -3) * 6, 585);
        g2.drawString("to", 1730, 240);
            if(currentlyBuying != null) {
                g2.drawString(currentlyBuying.getA().getName(), 1600, 200);
                g2.drawString(currentlyBuying.getB().getName(), 1600, 300);
            }

            
        g2.setFont(font);
    }

    if(action == 3) { //they wnana buy a station
        back.paint(g);
            confirm.paint(g);
            arrowup.paint(g);
            arrowdown.paint(g);

            g2.setFont(bigFont);
            g2.drawString("BACK", 1705, 987);
            g2.drawString("CONFIRM", 1664, 883);
            g2.drawString("Placing", 1694, 52);
            g2.drawString(arrayforchoosingroutecolor[routebuyingcolor] , 1675 - (arrayforchoosingroutecolor[routebuyingcolor].length() -5) * 8, 585);
        g2.drawString("Station", 1690, 97);
        if(theywannaplacestationon != null) {
            g2.setFont(font);
            g2.drawString(theywannaplacestationon.getName(), 1688 - (theywannaplacestationon.getName().length() -7) * 8, 474);
        }
    }

    for(Route r : game.getRoutes())
        {
            r.paint(g);
        }
        ArrayList<City> cities = game.getCities();
        for(int i = 0; i < cities.size(); i++)
        {
            cities.get(i).paint(g);
        }
        for(City a : game.getCities()) {
            if(a.hasStation()) {
                g.drawImage(stations[0], GamePanel.MAP_X + a.getXCoord() - 25/2 - 7, GamePanel.MAP_Y + a.getYCoord() - 25/2 - 12, stations[0].getWidth()/50, stations[0].getHeight()/50, null);
            }
        }
    if(buyingTunnel) {
        
        for(int c = 0; c < 3; c++){ 
            if(threecards[c] == routebuyingcolor) {
                extraCardsNeeded++; // how many cards match
            }
        }
        g2.setFont(font);    
        g2.setColor(Color.RED);  
        g2.drawImage(errorWindow, 725, 300, errorWindow.getWidth()/3, errorWindow.getHeight()/3, null);
        for(int c = 0; c < 3; c++) {
            g2.drawImage(rotatecounterclockwise(cards[threecards[c]]), 725 + errorWindow.getWidth()/32 + c*100, 300 + errorWindow.getHeight()/12, rotatecounterclockwise(cards[c]).getWidth()/5, rotatecounterclockwise(cards[c]).getHeight()/5, null);
        }
        g2.drawString("Number of extra cards needed:" + extraCardsNeeded, 725 + errorWindow.getWidth()/32, 340);
        acceptButton.paint(g);
        declineButton.paint(g);
        // g2.setColor(Color.RED);
        // g2.drawString("You need to pay " + nummatching + " extra cards", 665, 515);
        g2.setColor(Color.BLACK);
        g2.drawString("CONFIRM", 658, 664);
        g2.drawString("DECLINE", 1066, 664);
        System.out.println("num loco they want to use: " + numberoflocomotivestheywanttouse);
        System.out.println("extra" + extraCardsNeeded);
        System.out.println("how many they have of buying color: " + game.players[game.turn].numOfColor(routebuyingcolor));
        //game.buyTunnel(currentlyBuying, routebuyingcolor, extraCardsNeeded, numberoflocomotivestheywanttouse);
    }
}
    
    
    }

    public static void drawCenteredString(Graphics g, String text, int posX, int posY, int width, int height) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = posX + (width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = posY + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Draw the String
        g.drawString(text, x, y);
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();

            if(game.errorPanel == true) {
               
                if(okButton.isInside(x, y)) {
                    game.unerror(); //stop error
                }

            }

            else {// not error 
            if(action == -1) {
                if(endTurnButton.isInside(x, y)) { 
                    game.turn++; 
                    game.turn = game.turn % 4;

                    game.turnended = false;
                    action = 0;
                   System.out.println("they pressed the end turn button" + action);
                   repaint();
                   return;
                }

            }
            else if(action == 0) {
                
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
            if(buyingTunnel)
            {
                if(declineButton.isInside(x, y))
                {
                    game.discardtop3();
                    game.endTurn();
                }
                else if(acceptButton.isInside(x, y))
                {
                    game.buyRoute(currentlyBuying, numberoflocomotivestheywanttouse, routebuyingcolor, extraCardsNeeded);
                    System.out.println("extra cards needed: " + extraCardsNeeded);
                    game.endTurn();
                }
                buyingTunnel = false;
                extraCardsNeeded = 0;
            }
            if(back.isInside(x, y) && !buyingTunnel) { //if they click back button
                action = 0;
            }
            if(arrowup2.isInside(x, y)) {
                numberoflocomotivestheywanttouse++;
            }
            if(arrowdown2.isInside(x, y) && numberoflocomotivestheywanttouse > 0) {
                numberoflocomotivestheywanttouse--;
            }
            if(arrowdown.isInside(x, y) && !buyingTunnel) {
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

            if(!buyingTunnel)
            {
                for(int c =0; c< game.getRoutes().size(); c++) {
                
                        if(game.getRoutes().get(c).isInside(x, y)) {
                            currentlyBuying = game.getRoutes().get(c);
                            break;
                        }
                    }
              
            }
            if(!buyingTunnel && confirm.isInside(x, y) && currentlyBuying != null) { //biu route
                if(!buyingTunnel && currentlyBuying.isTunnel()) {
                    threecards = game.getThreeCards();
                    System.out.println("is buying tunnel");
                     buyingTunnel = true;
                }
                else {
                game.buyRoute(currentlyBuying, numberoflocomotivestheywanttouse, routebuyingcolor, 0);
                }

            }
        }
       else if(action == 3) { //buy station

            if(arrowdown.isInside(x, y)) {
                if(routebuyingcolor == 0) {
                    routebuyingcolor = 8;
                }
                else {
                    routebuyingcolor--;
                }
                
            }
            if(arrowup.isInside(x, y)) {
                if(routebuyingcolor == 8) {
                    routebuyingcolor = 0;
                }
                else {
                    routebuyingcolor++;
                }
                
            }


                    System.out.println("statioataotiaotan");
                    if (back.isInside(x, y)) { //if they click back button
                        action = 0;
                    }
                    if (confirm.isInside(x, y) && theywannaplacestationon != null) { //biu route
                        game.buyStation(theywannaplacestationon, routebuyingcolor);
                        System.out.println("they clicked confirm on buy station");
                    }
                    for (City a : game.getCities()) {
                        if (a.getButton().isInside(x, y)) {
                            theywannaplacestationon = a;
                            System.out.println("thaushdfusdfhusdfhusdfsdf");
                        }
                    }


        }
       
        if(game.turnended) { //if turn just ended u need to reset the screen yk
            action = -1;
           System.out.println("end of turn");
        }

        
        }
    }
        repaint();
    }

    public static BufferedImage rotatecounterclockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D g = rotatedImage.createGraphics();

        g.translate(0, width);
        g.rotate(-Math.PI / 2); // Rotate 90 degrees counterclockwise

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return rotatedImage;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

}