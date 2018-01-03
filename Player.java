import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Player implements Mover
{
	private Position pos;
	private Map map;
	private int dist1 = 1;
	private Timer fuse;
	
	public Player (Map map, int r, int c) {
		this.map = map;
		pos = new Position (r, c);
	}
	
	public Position getPos() {
		return pos;
	}

	//Check if space is valid before moving
	public boolean validMove (int r, int c)
	{
		if ((r >= 0 && r < map.getHeight()) && (c >= 0 && c < map.getWidth()))
			if (map.getSquare (r, c) != Map.WALL_SPACE &&  map.getSquare (r, c) != Map.DESTROYED_WALL)
				return true;

		return false;
	}

	public void move (int dir)
	{
		switch (dir)
		{
		case 1:
			if (validMove(pos.r,pos.c-dist1))	//Move down
				pos.c -= dist1;
			break;
		case 2:
			if (validMove(pos.r,pos.c+dist1))	//Move up
				pos.c += dist1;
			break;
		case 3:
			if (validMove(pos.r-dist1,pos.c))	//Move left
				pos.r -= dist1;
			break;
		case 4:
			if (validMove(pos.r+dist1,pos.c))	//Move right
				pos.r += dist1;
			break;
		}
	}
	
	//Create bomb, pass position and map to it
	public void deployBomb(Position pos, Map map) {
		Bomb bomb = new Bomb(pos, map);
	}
		
}
