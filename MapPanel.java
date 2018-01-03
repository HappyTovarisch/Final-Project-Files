import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;

public class MapPanel extends JPanel
{
	private Map map;
	private Enemy[] Enemies;
	private Player player;
	private int width, height, bSize;
	private Timer timer;
	private int wait = 1000;
	private int gameState;

	//Setting up the panel the map sits on
	public MapPanel (Map map, Player player, Enemy[] Enemies, int squareSize)
	{
		this.map = map;
		this.player = player;
		this.Enemies = Enemies;

		bSize = squareSize;
		width = map.getWidth();
		height = map.getHeight();

		gameState = 0;

		this.timer = new Timer (wait, null);
		timer.addActionListener(new EnemyTimer (timer, player, Enemies));
		timer.start();
	}

	//Checking if player got to end or Enemy 
	protected int checkWinLoss()
	{
		if (player.getPos().c == map.getFinish().c
				&& player.getPos().r == map.getFinish().r)
			gameState = 1;

		for (Enemy Enemy : Enemies)
			if (Enemy.getPos().c == player.getPos().c 
					&& Enemy.getPos().r == player.getPos().r)
				gameState = -1;
		
		return gameState;
	}

	//Coloring map, walls, Enemies, and player
	protected void paintComponent (Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent (g2);

		for (int r = 0; r < height; r++)
			for (int c = 0; c < width; c++)
			{
				Rectangle2D.Double bkgnd =
						new Rectangle2D.Double (c * bSize, r * bSize, bSize, bSize);
				if (map.getSquare(r,c) == Map.OPEN_SPACE)
					g2.setColor (Color.BLACK);
				else if (map.getSquare(r,c) == Map.WALL_SPACE)
					g2.setColor (Color.WHITE);
				else if (map.getSquare(r,c) == Map.DESTROYED_WALL)
					g2.setColor (Color.YELLOW);
				else if (map.getSquare(r,c) == Map.FINISH_SPACE)
					g2.setColor (Color.GREEN);
				else
					g2.setColor (Color.WHITE);
				g2.fill (bkgnd);
			}

		g2.setColor (Color.GREEN);	//Player
		g2.fillOval (player.getPos().c * bSize,
					player.getPos().r * bSize, bSize, bSize);

		g2.setColor (Color.RED);	//Basic Enemy
		for (Enemy Enemy: Enemies)
			g2.fillOval (Enemy.getPos().c * bSize,
						Enemy.getPos().r * bSize, bSize, bSize);	
	}
	
	//Defining how the Enemies move
	private class EnemyTimer implements ActionListener
	{
		private Player player;
		private Enemy[] enemies;
		private Timer timer;
		
		protected EnemyTimer (Timer t, Player player, Enemy[] enemies)
		{
			this.timer = t;
			this.player = player;
			this.enemies = enemies;
		}
		
		//Makes Enemies chase player
		public void actionPerformed (ActionEvent e)
		{
			for (Enemy chaser : enemies)
					chaser.chase (player);
		}
	}
}
