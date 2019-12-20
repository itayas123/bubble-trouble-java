import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RedBall extends Thread  {

	private GamePanel panel;
	private Image ballImage;
	private int x, y, size, dx, dy;
	private boolean isPaused, doBreak;

	public RedBall(GamePanel panel, int x)
	{
		this.panel= panel;
		this.size = 100;
		this.x = x- this.size;
		this.y = 200;
		this.dx = -10;
		this.dy = 10;
		this.isPaused = this.doBreak = false;
		ImageIcon img = new ImageIcon("images/Red_Ball.png");
		this.ballImage = img.getImage();
		start();
	} 
	
	public void run()
	{
		while(true)
		{
		
		   try {
			Thread.sleep(20);
		      } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		    }
		    this.move();
			panel.repaint();
			if (this.doBreak) {
				break;
			}
		}	
	}
	
	public void draw(Graphics g){
		g.drawImage(this.ballImage, this.x,this.y,this.size,this.size, null);
	}
	
	public void move() {
		this.x += this.dx;
		this.y += this.dy;
		if((this.y + this.size) >= (this.panel.getHeight() - 30)) {
			this.dy = -10;
		} else if (this.y <= 30) {
			this.dy = 10;
		}
		if((this.x + this.size) >= (this.panel.getWidth() - 30)) {
			this.dx = -5;
		} else if (this.x <= 30){
			this.dx = 5;
		}
		this.collision();
	}
	
	public void collision() {
		Bow bow = this.panel.bow;
		if (bow != null && bow.isAlive()) {
			int mxBall = this.getMiddleX();
			int myBall = this.getMiddleY();
			int largex = bow.getX() - this.getRadius();
			int largey = bow.getY() - this.getRadius();
			int w = bow.getSizex() + (2 * this.getRadius());
			int h = bow.getSizey() + (2 * this.getRadius());
			if(( (mxBall >= largex) && (mxBall <= largex + w)) && ( (myBall >= largey) && (myBall <= largey + h) ) ) {
				this.doBreak = true;
			}
		}
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public int getRadius() {
		return this.size/2;
	}
	
	public int getMiddleX() {
		return this.x + this.getRadius();
	}
	
	public int getMiddleY() {
		return this.y + this.getRadius();
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
