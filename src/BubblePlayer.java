import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BubblePlayer extends Thread   {
	
	final int PLAYER_SPEED = 12;

	private GamePanel panel;
	private Image playerImage;
	private int x, y, size;
	private boolean isPaused;
	private ImageIcon B, L, R;

	public BubblePlayer(GamePanel panel)
	{
		this.panel= panel;
		this.size = 100;
		this.x = (panel.getWidth()/2) - this.size + 50;
		this.y = panel.getHeight() - this.size - 30;
		this.isPaused = false;
		this.B = new ImageIcon("images/Man_B.png");
		this.L = new ImageIcon("images/Man_L.png");
		this.R = new ImageIcon("images/Man_R.png");
		this.playerImage = this.B.getImage();
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
			panel.repaint();
		}	
	}
	
	public void draw(Graphics g){
		g.drawImage(this.playerImage, this.x,this.y,this.size,this.size, null);
	}
	
	public void move(boolean left) {
		if(left && this.x > 30) {
			this.playerImage = this.L.getImage();
			this.x -= PLAYER_SPEED;
		} else if (!left && this.x < panel.getWidth() - this.size - 30) {
			this.playerImage = this.R.getImage();
			this.x += PLAYER_SPEED;
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

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
