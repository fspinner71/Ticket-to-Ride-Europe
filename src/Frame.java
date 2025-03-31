import javax.swing.JFrame;

public class Frame extends JFrame{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public Frame(String s)
    {
        super(s);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		add(new GamePanel());
		setVisible(true);
        setResizable(false); 

    }
}
