import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OuterFrame extends JFrame {
	
	JButton start;
	JButton info;
	OuterFrame outerFrame;
	JLabel Outer_Back;	
	Icon Outer_Background;
	JPanel outerPanel = new JPanel();

	
	public OuterFrame(){
		super("Bubble Trouble (c)");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setFocusable(false);
		this.setBackground(Color.red);
		 outerFrame = this;
		
Outer_Background = new ImageIcon("images/OuterFrame.jpg");
		
		Outer_Back = new JLabel(Outer_Background);
		Outer_Back.setBounds(0 , 0, 1025, 770);
		
		
		Font f = new Font("TimesRoman", Font.BOLD, 30);
		start = new JButton("Start");
		start.setBounds(300, 250, 350, 100);
		start.setFont(f);		
		start.setBackground(Color.green);
		
		info = new JButton("Info");
		info.setBounds(300 , 400, 350, 100);
		info.setFont(f);
		info.setBackground(Color.blue);
		info.setForeground(Color.white);

		outerPanel.setLayout(null);
		
		outerPanel.add(start);
		outerPanel.add(info);
		outerPanel.add(Outer_Back);
		
		this.add(outerPanel,BorderLayout.CENTER);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFrame frame = new JFrame();
				GamePanel gp = new GamePanel(frame);
				frame.setSize(gp.width, gp.height);
				frame.setUndecorated(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setFocusable(false);
				frame.add(gp);
				gp.hideMouseCursor();
			}
		});
		
		info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(outerFrame,"Bubble Trouble\n*Arrow Key = Move Man \n*Space = Shot Bow\n*Enter = Start Again/ Over\n*Esc = Quit Game\n Cheats: \n*L = 999 Lives \n*C = No Player Collision" );
			}
		});
	}
	
	public static void main(String[] args) {
		
		new OuterFrame();

	}
}
