import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bow extends Thread {
	
	private GamePanel panel;
	private Image bowImage;
	private int x, y, sizex,sizey, dy;
	private boolean isPaused;

	public Bow(GamePanel panel, BubblePlayer player)
	{
		this.panel= panel;
		this.sizex = this.sizey = 35;
		this.x = player.getX() + (player.getSize() / 2) - (this.sizex / 2);
		this.y = player.getY() - this.sizey;
		this.dy = 10;
		this.isPaused = false;
		ImageIcon img = new ImageIcon("images/Bow.png");
		this.bowImage = img.getImage();
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
		   this.sizey += this.dy;
		   this.y -= this.dy;
		   panel.repaint();
		   if((this.sizey - this.y) >= (this.panel.getHeight() - 30)) {
			   break;
		   }
		}	
	}
	
	public void draw(Graphics g){
		g.drawImage(this.bowImage, this.x,this.y,this.sizex,this.sizey, null);
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
	
	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}
