import javax.swing.JFrame;

public class Frame extends JFrame{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private GamePanel game;
    private EndPanel end;

    public Frame(String s)
    {
        super(s);

        game = new GamePanel(this);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		add(game);
		setVisible(true);
        setResizable(false); 

    }
    public void endGame()
    {
        Game g = game.getGame();
        end = new EndPanel(g);
        game.setVisible(false);
        remove(game);
        add(end);
    }
}
