package project.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.game.Game;
import project.server.Server;

public class StartFrame extends JFrame {

	public StartFrame(){
		super("Game Changer");
		initialise();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setPreferredSize(new Dimension(100, 100));
		setVisible(true);
	}

	public void initialise(){

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 10, 10));

		JButton button = new JButton("Host");

		JLabel title = new JLabel("Host or Join?", JLabel.CENTER);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Server server = new Server(new Game());
				server.start();
				dispose();
			}
		});
		buttons.add(button);
		button = new JButton("Join");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Create new GUI
			}
		});

        setPreferredSize(new Dimension(400, 70));

		buttons.add(button);
		add(buttons, BorderLayout.SOUTH);
		add(title, BorderLayout.NORTH);

	}
}
