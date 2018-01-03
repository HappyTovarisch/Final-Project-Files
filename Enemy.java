
public class Enemy implements Mover
{

	private Position pos;
	private Map map;
	private int down = 1;
	private int up = 2;
	private int left = 3;
	private int right = 4;
	
	public Enemy (Map map, int r, int c) {
		this.map = map;
		pos = new Position (r, c);
	}
	
	public Position getPos() {
		return pos;
	}

	public void chase (Player player)
	{
		int colDelta = pos.c - player.getPos().c; //Check distance between creature position and player position
		int rowDelta = pos.r - player.getPos().r;

		boolean horzMove = (Math.abs(colDelta) > Math.abs(rowDelta)); //horzMove = true if the column distance is greater than row distance

		if (horzMove) //If vertical move is needed
		{
			if (colDelta > 0)
				move (down); //Move down
			else
				move (up); //Move up
		}
		else		//If horizontal move is needed
		{
			if (rowDelta > 0)
				move (left); //Move left
			else
				move (right); //Move right
		}
	}
	
	//Check if space is valid before moving
	public boolean validMove (int r, int c)
	{
		if ((r >= 0 && r < map.getHeight()) && (c >= 0 && c < map.getWidth()))
			if (map.getSquare (r, c) != Map.WALL_SPACE)
				return true;

		return false;
	}
	
	public void move (int dir)
	{
		switch (dir)
		{
		case 1:
			if (validMove(pos.r,pos.c-down)) //Move down
				pos.c -= down;
			break;
		case 2:
			if (validMove(pos.r,pos.c+down)) //Move up
				pos.c += down;
			break;
		case 3:
			if (validMove(pos.r-down,pos.c)) // Move left
				pos.r -= down;
			break;
		case 4:
			if (validMove(pos.r+down,pos.c)) // Move right
				pos.r += down;
			break;
		}
	}
	
}

