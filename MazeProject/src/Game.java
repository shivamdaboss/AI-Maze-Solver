import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

public class Game {
	
	public static int FRAME_WIDTH = 1750, FRAME_HEIGHT = 1800, TILE_HEIGHT = 17, TILE_WIDTH = 17;
	public static JFrame frame;
	public static Canvas canvas;
	public static BufferStrategy bs;
	public static Graphics g;
	public static Maze m;
	public static TileNode start, goal;
	public static Player p;
	public static boolean going = true;
	public static Statistics stat;
	public Game(Statistics stat) {
		this.stat = stat;
		init();
		going = true;
		while(going) {
			if(!Runner.trials)
				draw();
			refresh();
		}
		if(!Runner.trials)
			destroy();
	}
	
	public void init() {
		if(!Runner.trials) {
			frame = new JFrame("Maze");
			canvas = new Canvas();
			frame.setSize(FRAME_WIDTH+1, FRAME_HEIGHT + 38);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setFocusable(true);
			frame.setResizable(true);
			canvas.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			canvas.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			canvas.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			frame.add(canvas);
			frame.setVisible(true);
		}
		m = new Maze();
		int x = (int)(Math.random() * 101);
		int y = (int)(Math.random() * 101);
		while(m.maze[x][y].getStatus()) {
			x = (int)(Math.random() * 101);
			y = (int)(Math.random() * 101);
		}
		start = new TileNode(false, x, y);
		p = new Player(x, y, m);
		while(m.maze[x][y].getStatus() || (x == start.getX() || y == start.getY())) {
			x = (int)(Math.random() * 101);
			y = (int)(Math.random() * 101);
		}
		goal = new TileNode(false, x, y);
	}
	
	
	public void draw() {
		if(canvas.getBufferStrategy() == null)
			canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		//drawing begins here
			//drawing ends here
		m.draw(g, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(Color.blue);
		g.fillRect(start.getX()*TILE_WIDTH, start.getY()*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(Color.red);
		g.fillRect(goal.getX()*TILE_WIDTH, goal.getY()*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(Color.green);
		g.fillRect(p.getX()*TILE_WIDTH, p.getY()*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g.dispose();
		bs.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void refresh() {
		p.move();
		
	}
	
	public void destroy() {
		frame.dispose();
		frame = null; 
		canvas = null;
	}
}
