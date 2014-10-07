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
import javax.swing.JTextField;

import project.client.Client;
import project.client.User;

import project.game.Game;
import project.net.DummyEvent;
import project.server.Server;

public class StartFrame extends JFrame {
	
	private JPanel buttons;
	private JPanel hostInfo;
	private JTextField ip;
	private JTextField name;
	private JButton button;
	private JLabel title;
	

	public StartFrame(){
		super("Game Changer");
		initialise();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setPreferredSize(new Dimension(100, 100));
		setVisible(true);
	}

	public void initialise(){

		buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 10, 10));

		button = new JButton("Host");

		title = new JLabel("Host or Join?", JLabel.CENTER);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newServer();
			}
		});
		buttons.add(button);
		button = new JButton("Join");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				remove(buttons);
				title.setText("Please Enter the Host Information below:");
				newGUI();
			}
		});

        setPreferredSize(new Dimension(400, 120));

		buttons.add(button);
		add(buttons, BorderLayout.SOUTH);
		add(title, BorderLayout.NORTH);
	}
	
	private void newServer(){
		Server server = new Server(new Game());
		server.start();
	}
	
	private void newGUI(){
		hostInfo = new JPanel();
		ip = new JTextField("localhost", 15);
		name = new JTextField("User Name", 15);
		button = new JButton("Connect");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 String ipAddress = ip.getText();
				 String username = name.getText();
				 
				 User user = new User(ipAddress, username);
				 
				 Panel panel = new Panel(user);
				 Client client = user.getClient();
				 
				 // -- TODO: Fix this!
				// client.push(new DummyEvent());
				 client.update();
				 dispose();
				 
				 
			}
		});
		hostInfo.add(ip);
		hostInfo.add(name);
		hostInfo.add(button);
		add(hostInfo);
		setVisible(true);
	}
	
	
}
