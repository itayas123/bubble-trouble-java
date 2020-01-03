import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel  extends JPanel{

	public BubblePlayer player;
	public List<RedBall> redBalls = new ArrayList<RedBall>();
	public Bow bow;
	public boolean isPaused;
	private Image backGroundImage;
	private JFrame jf;

	public int width, height;
	
	public GamePanel(JFrame jframe){
		ImageIcon ii =new ImageIcon("images/background.jpg");
		backGroundImage= ii.getImage();
		
		this.jf = jframe;
	
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.width = (int) (gd.getDisplayMode().getWidth() * 0.8);
		this.height = (int) (gd.getDisplayMode().getHeight() * 0.8);
		isPaused = false;
		player = new BubblePlayer(this);
		for(int i=0; i<2; i++) {
			int x = 30;
			if(i%2==0)
				x += this.getWidth();
			redBalls.add(new RedBall(this,x,230, 100, true));		
		}
		addKeyListener(new KL (this));
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		g.drawImage(this.backGroundImage, 0,0,this.width,this.height, null);
		player.draw(g);
		
		for(int i=0; i< redBalls.size(); i++) {
			RedBall ball = redBalls.get(i);
			if(ball != null && ball.isAlive() && !ball.doBreak)
				ball.draw(g);
		}
		
		if (bow != null && bow.isAlive())
			bow.draw(g);
		
	}
	
	public void  hideMouseCursor(){
		 //Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorimg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JPanel.
		setCursor(blankCursor);	
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	 class KL extends KeyAdapter
     {
		 private GamePanel panel;
		 public KL(GamePanel panel) {
			 this.panel = panel;
		 }
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.move(true);
				break;
			case KeyEvent.VK_RIGHT:
				player.move(false);
				break;
			case KeyEvent.VK_SPACE:
				if(bow == null || (bow != null && !bow.isAlive()))
					bow = new Bow(this.panel, player);
				break;
			case KeyEvent.VK_ESCAPE:
				jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
				break;
			default:
				break;
			}
		}
		
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
				player.setDefaultImg();
				break;
			default:
				break;
			}
		}
     }
	public static void main(String[] args) {
		JFrame jframe=new JFrame("Bubble Trouble (c)");
		GamePanel gp=new GamePanel(jframe);
		jframe.setSize(gp.width, gp.height);
		jframe.add(gp);
		jframe.setUndecorated(true);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setVisible(true);	
		jframe.setFocusable(false);
		gp.hideMouseCursor();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
