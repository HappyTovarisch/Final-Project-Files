import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanel extends JPanel
{
	private Map map;
	private Player player;
	private MapPanel mapPanel;
	private Enemy[] enemies;
	private Timer timer;
	private int levels = 1;
	
	//For scaling number of Enemies
	int numEnemies = 0;
	
	//For setting original number of Enemies
	int numEnemies1;


	public GamePanel (int numEnemies1)
	{
		this.numEnemies1 = numEnemies1;
		
		JOptionPane rules = new JOptionPane("Oh, no!");
		rules.showMessageDialog(null, "You've been thrown in the dungeon!\nUse ARROW KEYS to move\nUse SPACE to drop bomb\nESCAPE!");
		setBoard(numEnemies1);
	}
	
	//Setting and re-setting the board
	public void setBoard (int numEnemies) {
		
		this.numEnemies = numEnemies;
		
		
		// create a 70x50 map w/ 10x10-pixel blocks
		int width = 80;
		int height = 80;
		int bSize = 10;
		map = new Map (width, height);

		player = new Player (map, 1, 1);
		enemies = new Enemy[numEnemies];

		//Placing Enemies
		for (int i = 0; i < numEnemies; i++)
		{
			Random temp = new Random();
			enemies[i] = new Enemy (map, temp.nextInt(height-2)+1,
												temp.nextInt(width-2)+1);
		}
				

		//Setting up map
		mapPanel = new MapPanel (map, player, enemies, bSize);
		mapPanel.setPreferredSize (new Dimension ((width+2) * bSize, (height+1) * bSize));
		mapPanel.addKeyListener (new PlayerController (player));
		mapPanel.setFocusable (true);

		setLayout (new FlowLayout());
		add (mapPanel);

		//Create the timer
		this.timer = new Timer (30, new TimerListener());
		timer.start();
	}

	/**
	 * check the game status every X milliseconds
	 * 		(as defined upon Timer creation).
	 */
	private class TimerListener implements ActionListener
	{
		JOptionPane gameOver = new JOptionPane("Game over!");
		public void actionPerformed (ActionEvent e)
		{
			mapPanel.repaint();

			if (mapPanel.checkWinLoss() == 1) {	
				levels++;						//If game won, display message, go to next level
				gameOver.showMessageDialog(null, "Level " + levels);
				timer.stop();
				mapPanel.setVisible(false);
				setBoard(numEnemies+15);

			}
			else if  (mapPanel.checkWinLoss() == -1) {	//If lost, display message and reset game
				levels = 1;
				gameOver.showMessageDialog(null, "You were caught and returned to your cell!");
				timer.stop();
				mapPanel.setVisible(false);
				setBoard(numEnemies1);
			}
		}
	}

	//Player controls - arrowkeys
	private class PlayerController extends KeyAdapter
	{
		private Player player;

		public PlayerController (Player player) {
			this.player = player;
		}

		public void keyPressed (KeyEvent e)
		{
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				player.move(1);
				break;
			case KeyEvent.VK_RIGHT:
				player.move(2);
				break;
			case KeyEvent.VK_UP:
				player.move(3);
				break;
			case KeyEvent.VK_DOWN:
				player.move(4);
				break;
			case KeyEvent.VK_SPACE:
				player.deployBomb(player.getPos(), map);
				mapPanel.validate();
				mapPanel.repaint();
				break;
			}
		}
	}
}
