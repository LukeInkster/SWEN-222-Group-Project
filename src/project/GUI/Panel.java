package project.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel extends JPanel{

	JFrame frame;
	
	public Panel(){
		
		frame = new JFrame("Game Changer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
	}
}
