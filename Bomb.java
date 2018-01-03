import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bomb {
	
	private static Position pos;
	private Map map;
	private Timer fuse;
	private Timer timer;

	public Bomb (Position BombPos, Map BombMap) {
		pos = BombPos;
		map = BombMap;
		
		timer = new Timer(500, new TimerListener());
		timer.start();

		fuse = new Timer(1000, new FuseListener());
		fuse.start();
	}
	
	public Position getPos() {
		return pos;
	}
	
	public void Remove(Position pos) {
//		System.out.println("pos = (" + pos.r + "," + pos.c + ")");
//		System.out.flush();
		for (int r = pos.r-1; r <= pos.r+1; r++ ) {
			for (int c = pos.c-1; c <= pos.c+1; c++) {
				map.setSquare(r, c, map.OPEN_SPACE);
			}
		}
	}
	
	public void Explode(Position pos) {
		for (int r = pos.r-1; r <= pos.r+1; r++ ) {
			for (int c = pos.c-1; c <= pos.c+1; c++) {
				map.setSquare(r, c, map.DESTROYED_WALL);
			}
		}
	}
	
	private class FuseListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			Remove(pos);
			fuse.stop();

		}
	}
	
	
	private class TimerListener implements ActionListener {
		
		public void actionPerformed (ActionEvent e) {

			Explode(pos);
			timer.stop();
		}
	}
}
