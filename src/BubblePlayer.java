import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BubblePlayer extends Thread {

	final int PLAYER_SPEED = 12;

	private GamePanel panel;
	private Image playerImage;
	private int x, y, width, height, lives;
	private ImageIcon B, L, R;
	private boolean moveLeft, moveRight, checkCollision;

	public BubblePlayer(GamePanel panel, int lives, boolean checkCollision) {
		this.panel = panel;
		this.height = 100;
		this.width = 80;
		this.lives = lives;
		this.moveLeft = this.moveRight = false;
		this.checkCollision = checkCollision;
		this.x = (panel.getWidth() / 2) - this.width + 50;
		this.y = panel.getHeight() - this.height - panel.FRAME_SIZE;
		this.B = new ImageIcon("images/Man_B.png");
		this.L = new ImageIcon("images/Man_L.png");
		this.R = new ImageIcon("images/Man_R.png");
		this.playerImage = this.B.getImage();
		start();
	}

	public void run() {
		while (true) {
			if (this.panel.isPaused) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.move();
			panel.repaint();

		}
	}

	public void draw(Graphics g) {
		g.drawImage(this.playerImage, this.x, this.y, this.width, this.height, null);
	}

	public void move() {
		if (this.moveLeft && this.x > panel.FRAME_SIZE) {
			this.x -= PLAYER_SPEED;
			this.playerImage = this.L.getImage();
		}
		if (this.moveRight && this.x < panel.getWidth() - this.width - panel.FRAME_SIZE) {
			this.x += PLAYER_SPEED;
			this.playerImage = this.R.getImage();
		}
	}

	public void setDefaultImg() {
		this.playerImage = this.B.getImage();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setY(int y) {
		this.y = y;
	}
	

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean checkCollision() {
		return checkCollision;
	}

	public void setCheckCollision(boolean checkCollision) {
		this.checkCollision = checkCollision;
	}
}
