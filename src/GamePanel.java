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

		player = new BubblePlayer(this);
		for (int i = 0; i < level; i++) {
			int x = FRAME_SIZE;
			if (i % 2 == 0)
				x += getWidth();
			redBalls.add(new RedBall(this, x, 230, level * 25, true));
		}

		addKeyListener(new KL(this));
		setFocusable(true);
	}

	public void startOver() {
		isPaused = false;
		redBalls.clear();
		bow = null;
		for (int i = 0; i < 2; i++) {
			int x = FRAME_SIZE;
			if (i % 2 == 0)
				x += getWidth();
			redBalls.add(new RedBall(this, x, 230, level * 25, true));
		}
		synchronized (player) {
			player.notify();
			player = new BubblePlayer(this);
		}
	}

	public void levelUp() {
		level++;
		isPaused = true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startOver();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, width, height, null);

		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g.setColor(Color.yellow);
		g.drawString("Level:" + level, 50, 70);

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
					if (bow == null || (bow != null && !bow.isAlive()))
						bow = new Bow(this.panel, player);
					break;
				case KeyEvent.VK_ESCAPE:
					jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
					break;
				case KeyEvent.VK_ENTER:
					if (this.panel.isPaused) {
						startOver();
					}
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

	public static void main(String[] args) {
		JFrame jframe = new JFrame("Bubble Trouble (c)");
		GamePanel gp = new GamePanel(jframe);
		jframe.setSize(gp.width, gp.height);
		jframe.add(gp);
		jframe.setUndecorated(true);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.setFocusable(false);
		gp.hideMouseCursor();

	}
}
