import java.util.concurrent.ThreadLocalRandom;

public class Map
{
	protected final static int INVALID_SQUARE = -1;
	protected final static int OPEN_SPACE = 0;
	protected final static int WALL_SPACE = 1;
	protected final static int START_SPACE = 2;
	protected final static int FINISH_SPACE = 3;
	protected final static int DESTROYED_WALL = 4;

	private int[][] squares;
	private int width, height;
	private Position start = null;
	private Position finish = null;
	

	public Map (int width, int height)
	{
		this.width = width;
		this.height = height;
		squares = new int[height][width];
		
		int FINISH_C = ThreadLocalRandom.current().nextInt(20, width-2);
		int FINISH_R = ThreadLocalRandom.current().nextInt(20, height-2);

		//Wall creation code
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				
				int CHANCE = ThreadLocalRandom.current().nextInt(0, 2);
		
				if (r == 0 || r == height-1 || c == 0 || c == width-1) {
					squares[r][c] = WALL_SPACE;
				}
				
				else if (r < 5 && c < 5) squares[r][c] = OPEN_SPACE;
				
				else if (CHANCE == 1) {
					squares[r][c] = WALL_SPACE;
				}
				
					
				else if (CHANCE != 1) {
					squares[r][c] = OPEN_SPACE;
				}

		setStart (1, 1);
		setFinish (FINISH_R, FINISH_C);
			}
		}

	}

	//Setting the start space
	public void setStart (int r, int c)
	{
		Position p = new Position (r, c);

		if (validPosition (p))
		{
			squares[p.r][p.c] = START_SPACE;
			start = p;
		}
		else
			start = null;
	}

	protected Position getStart () {
		return start;
	}

	//Setting the finish space
	public void setFinish (int r, int c)
	{
		Position p = new Position (r, c);

		if (validPosition (p))
		{
			squares[p.r][p.c] = FINISH_SPACE;
			finish = p;
		}
		else
			finish = null;
	}

	//Setters and getters
	protected Position getFinish () {
		return finish;
	}

	public int getWidth() {
		return width;	
	}										

	public int getHeight(){
		return height;
	}

	protected int getSquare (int row, int col)
	{
		if (validPosition (row, col))
			return squares[row][col];
		else
			return INVALID_SQUARE;
	}

	protected int getSquare (Position pos) {
		return getSquare (pos.r, pos.c);
	}
	
	protected void setSquare (int row, int col, int TYPE) {
		if (validPosition (row, col))
			squares[row][col] = TYPE;
	}
	
	protected void setSquare (Position pos, int TYPE) {
		squares[pos.r][pos.c] = TYPE;
	}

	protected boolean validPosition (int row, int col)
	{
		if (row < 0)
			return false;
		else if (row >= height)
			return false;

		if (col < 0)
			return false;
		else if (col >= width)
			return false;

		return true;
	}

	protected boolean validPosition (Position pos) {
		return validPosition (pos.r, pos.c);
	}
}
