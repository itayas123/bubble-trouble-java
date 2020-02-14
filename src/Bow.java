import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bow extends Thread {

	private GamePanel panel;
	private Image bowImage;
	private int x, y, sizex, sizey, dy;
	boolean doBreak;

	public Bow(GamePanel panel, BubblePlayer player) {
		this.panel = panel;
		this.sizex = this.sizey = 15;
		this.x = player.getX() + (player.getWidth() / 2) - (this.sizex / 2);
		this.y = player.getY() - this.sizey;
		this.dy = 15;
		this.doBreak = false;
		ImageIcon img = new ImageIcon("images/Bow.png");
		this.bowImage = img.getImage();
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
			this.sizey += this.dy;
			this.y -= this.dy;
			panel.repaint();
			if ((this.sizey - this.y) >= (this.panel.getHeight() - 30)) {
				break;
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(this.bowImage, this.x, this.y, this.sizex, this.sizey, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSizex() {
		return sizex;
	}

	public int getSizey() {
		return sizey;
	}

}
