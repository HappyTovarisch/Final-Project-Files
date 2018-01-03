
//Interface for player and creatures
public interface Mover {
	
	Position getPos();

	boolean validMove (int r, int c);

	void move (int dir);

}

