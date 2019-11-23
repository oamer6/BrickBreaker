package brickBreaker;

import javax.swing.JFrame;

public class frame {
	public static void main(String args[]) {
		JFrame obj = new JFrame(); 
		gamePlay game = new gamePlay();
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Break");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
	}
}
