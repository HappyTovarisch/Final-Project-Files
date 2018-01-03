import javax.swing.*;

public class MazeGame
{
	public static void main (String[] args)
	{
		int numCreatures;

		JFrame frame = new JFrame ("Maze");

		//If there are no imput args
		if (args.length < 1) {
			numCreatures = 35;
		}
		
		else {
			numCreatures = Integer.parseInt (args[0]);		
		}
		
		GamePanel panel = new GamePanel (numCreatures);
		frame.add (panel);

		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible (true);
	}
}
