package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class gamePlay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	private int brickCount = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerA = 310; 	// starting position of the slider
	private int ballposX = 120; // ball position
	private int ballposY = 350; // ball position
	
	private int ballxDir = -1; 	// ball direction
	private int ballyDir = -2;	// ball direction
	
	private brickLayout map;
	
	public gamePlay() {
		map = new brickLayout(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
									
		g.setColor(Color.black); 	// background
		g.fillRect(1, 1, 692, 592);	// creating rectangle for background
		
		map.draw((Graphics2D)g);	// drawing map
		
									// borders
		g.setColor(Color.blue);
		g.fillRect(0, 0, 3, 592); 	// we only create three borders (right, up, left), bottom is open
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691 , 0, 3, 592);
		
									// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
								
									// paddle
		g.setColor(Color.red);
		g.fillRect(playerA, 550, 100, 8);
		
									// ball
		g.setColor(Color.orange);
		g.fillOval(ballposX, ballposY, 20, 20);
		
									// victory
		if (brickCount <= 0) {
			play = false;
			ballxDir = 0;
			ballyDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Victory", 260, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
									// game over
		if (ballposY > 570) {
			play = false;
			ballxDir = 0;
			ballyDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over", 225, 350);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 200, 450);
		}
		
		g.dispose();
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		if (play)
		{
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerA, 550, 100, 8)))	// detecting intersection of ball with the player
			{
				ballyDir = -ballyDir;
			}
			
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0 ) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickVal(0, i, j);
							brickCount--;
							score+= 5;
							
							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballxDir = -ballxDir;
							}
							else {
								ballyDir = -ballyDir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballxDir;
			ballposY += ballyDir;
			if (ballposX < 0)	// left border for the ball
			{
				ballxDir = -ballxDir;
			}
			if (ballposY < 0)	// top border for the ball
			{
				ballyDir = -ballyDir;
			}
			if (ballposX > 670)	// right border for the ball
			{
				ballxDir = -ballxDir;
			}
			
		}
		repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {} 	// no use for this game

	@Override
	public void keyTyped(KeyEvent arg0) {}		// no use for this game
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (playerA >= 600) // checking to see if player goes outside of the panel
			{
				playerA = 600;
			}
			else
			{
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (playerA < 10) // checking to see if player goes outside of the panel
			{
				playerA = 10;
			}
			else
			{
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballxDir = -1;
				ballyDir = -2;
				playerA = 310;
				score = 0;
				brickCount = 21;
				map = new brickLayout(3, 7);
				
				repaint();
			}
		}
		
	}
	
	public void moveRight() {	// if right key is pressed, this will move playerA 20 pixels to the right
		play = true;
		playerA += 20;	
	}
	
	public void moveLeft() {	// if left key is pressed, this will move playerA 20 pixels to the left
		play = true;
		playerA -= 20;
	}

}
