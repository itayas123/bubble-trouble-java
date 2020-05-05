import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
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

public class GamePanel extends JPanel {

	final int FRAME_SIZE = 30;
	final int BALL_SIZES[] = {10, 25, 50, 100, 200, 300};

	public BubblePlayer player;
	public List<RedBall> redBalls = new ArrayList<RedBall>();
	public Bow bow;
	public boolean isPaused;
	public int level;
	private Image backGroundImage;
	private JFrame jf;

	public int width, height;

	public GamePanel(JFrame jframe) {
		ImageIcon ii = new ImageIcon("images/background.jpg");
		backGroundImage = ii.getImage();

		jf = jframe;

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = (int) (gd.getDisplayMode().getWidth() * 0.8);
		height = (int) (gd.getDisplayMode().getHeight() * 0.8);

		level = 1;
		isPaused = false;

		player = new BubblePlayer(this, 3, true);
		addBalls();
		
		addKeyListener(new KL(this));
		setFocusable(true);
	}
	
	public void addBalls() {
		for (int i = 0; i < (level > 1 ? 2 : 1); i++) {
			int x = FRAME_SIZE;
			if (i % 2 == 0)
				x += getWidth();
			redBalls.add(new RedBall(this, x, 230, BALL_SIZES[level], true));
		}
	}

	public void startOver() {
		if (player.getLives() == 0) {
			level = 1;
			player.setLives(3);
		}
		
		isPaused = false;
		redBalls.clear();
		bow = null;
		addBalls();
		synchronized (player) {
			player.notify();
			player = new BubblePlayer(this, player.getLives(), player.checkCollision());
		}
		
	}
	
	public void continuePlay() {
		isPaused = false;
		synchronized (player) {
			player.notify();
		}
		if (bow != null && bow.isAlive()) {
			synchronized (bow) {
				bow.notify();
			}
		}
		for (RedBall ball : redBalls) {
			synchronized (ball) {
				ball.notify();
			}
		}
		
	}

	public void levelUp() {
		if (level < BALL_SIZES.length - 1) {
			level++;
			isPaused = true;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startOver();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, width, height, null);

		// level
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g.setColor(Color.yellow);
		g.drawString("Level:" + level, 50, 70);
		
		// lives
		g.setColor(Color.red);
		g.drawString("lives:" + player.getLives(), getWidth() - 175, 70);

		// draw the bow and the player again
		if (bow != null && bow.isAlive()) {
			bow.draw(g);
		}

		// draw the player
		player.draw(g);
		
		int counter = 0;
		// draw the balls that alive
		for (int i = 0; i < redBalls.size(); i++) {
			RedBall ball = redBalls.get(i);
			if (ball != null && ball.isAlive() && !ball.doBreak) {
				ball.draw(g);
				counter++;
			}
		}
		
		if(isPaused  && player.getLives() == 0) {
			g.drawImage((new ImageIcon("images/GameOver.png")).getImage(), (getWidth()/2) -250, (getHeight()/2) -250, 500, 500, null);
		}
		if (counter == 0) {
			levelUp();
		}

	}

	public void hideMouseCursor() {
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JPanel.
		setCursor(blankCursor);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	class KL extends KeyAdapter {
		private GamePanel panel;

		public KL(GamePanel panel) {
			this.panel = panel;
		}

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setMoveLeft(true);
					break;
				case KeyEvent.VK_RIGHT:
					player.setMoveRight(true);
					break;
				case KeyEvent.VK_SPACE:
					if (bow != null && bow.isAlive()) {
						bow.doBreak = true;
					}
					bow = new Bow(this.panel, player);
					break;
				case KeyEvent.VK_ESCAPE:
					jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
					break;
				case KeyEvent.VK_ENTER:
					if (this.panel.isPaused) {
						startOver();
					}
					break;
				case KeyEvent.VK_L:
					player.setLives(999);
					break;
				case KeyEvent.VK_P:
					if(isPaused) {
						continuePlay();
					} else {
						isPaused = true;						
					}
					break;
				case KeyEvent.VK_4:
					level = 4;
					startOver();
					break;
				case KeyEvent.VK_C:
					player.setCheckCollision(false);
				default:
					break;
			}
		}

		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setMoveLeft(false);
					player.setDefaultImg();
					break;
				case KeyEvent.VK_RIGHT:
					player.setMoveRight(false);
					player.setDefaultImg();
					break;
				default:
					break;
			}
		}
	}

}
