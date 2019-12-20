import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RedBall extends Thread  {

	
	final int BALL_SPEED = 12;

	private GamePanel panel;
	private Image ballImage;
	private int x, y, size, dx, dy;
	private boolean isPaused;

	public RedBall(GamePanel panel, int x)
	{
		this.panel= panel;
		this.size = 100;
		this.x = x- this.size;
		this.y = 200;
		this.dx = -10;
		this.dy = 10;
		this.isPaused = false;
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
	}
	
	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
