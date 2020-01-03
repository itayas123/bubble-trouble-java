import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RedBall extends Thread {

	private GamePanel panel;
	private Image ballImage;
	private int x, y, size, a, b;
	boolean doBreak;

	public RedBall(GamePanel panel, int x, int y, int size, boolean direction) {
		this.panel = panel;
		this.size = size;
		this.x = x - this.size;
		this.y = y - this.size;
		this.a = direction ? 10 : -10;
		this.b = 10;
		this.doBreak = false;
		ImageIcon img = new ImageIcon("images/Red_Ball.png");
		this.ballImage = img.getImage();
		start();
	}

	public void run() {
		while (!doBreak) {
			try {
				if (this.panel.isPaused) {
					synchronized (this) {
						wait();
					}
				}

				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.move();
			this.bowCollision();
			if (doBreak) {
				if (this.getRadius() > 20) {
					panel.redBalls
							.add(new RedBall(panel, this.getMiddleX(), this.getMiddleY(), this.getRadius(), true));
					panel.redBalls
							.add(new RedBall(panel, this.getMiddleX(), this.getMiddleY(), this.getRadius(), false));
				}
				int index = panel.redBalls.indexOf(this);
				if (index != -1)
					panel.redBalls.remove(index);
			}
			this.playerCollision();
			panel.repaint();
		}
	}

	public void draw(Graphics g) {
		g.drawImage(this.ballImage, this.x, this.y, this.size, this.size, null);
	}

	public void move() {
		this.x += this.a;
		this.y += this.b;
		if ((this.y + this.size) >= (this.panel.getHeight() - 30)) {
			this.b = -10;
		} else if (this.y <= 30) {
			this.b = 10;
		}
		if ((this.x + this.size) >= (this.panel.getWidth() - 30)) {
			this.a = -5;
		} else if (this.x <= 30) {
			this.a = 5;
		}
	}

	public void bowCollision() {
		Bow bow = this.panel.bow;
		if (bow != null && bow.isAlive()) {
			int mxBall = this.getMiddleX();
			int myBall = this.getMiddleY();
			int largex = bow.getX() - this.getRadius();
			int largey = bow.getY() - this.getRadius();
			int w = bow.getSizex() + (2 * this.getRadius());
			int h = bow.getSizey() + (2 * this.getRadius());
			if (((mxBall >= largex) && (mxBall <= largex + w)) && ((myBall >= largey) && (myBall <= largey + h))) {
				this.doBreak = true;
				bow.doBreak = true;
			}
		}
	}

	public void playerCollision() {
		BubblePlayer player = this.panel.player;
		if (player != null && player.isAlive()) {
			int mxBall = this.getMiddleX();
			int myBall = this.getMiddleY();
			int largex = player.getX() - this.getRadius();
			int largey = player.getY() - this.getRadius();
			int w = player.getSize() + (2 * this.getRadius());
			int h = player.getSize() + (2 * this.getRadius());
			if (((mxBall >= largex) && (mxBall <= largex + w)) && ((myBall >= largey) && (myBall <= largey + h))) {
				this.panel.isPaused = true;
			}
		}
	}

	public int getRadius() {
		return this.size / 2;
	}

	public int getMiddleX() {
		return this.x + this.getRadius();
	}

	public int getMiddleY() {
		return this.y + this.getRadius();
	}
}
