import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Maze {
	
	public TileNode[][] maze;
	public HashMap<TileNode, ArrayList<TileNode>> alist, alistWeird;
	
	public Maze() {
		maze = new TileNode[101][101];
		for(int i = 0; i < 101; i++) {
			for(int j = 0; j < 101; j++) {
				if(i % 2 == 1 || j % 2 == 1)
					maze[i][j] = new TileNode(true, i, j);
				else
					maze[i][j] = new TileNode(false, i, j);
			}
		}
		
		alist = generateAList(maze);


		
		int[][] visited = new int[101][101];
		for(int i = 0; i < 101; i++) {
			for(int j = 0; j < 101; j++) {
				visited[i][j] = 0;
			}
		}
		
		
		Stack<TileNode> path = new Stack<TileNode>();
		ArrayList<TileNode> temp = new ArrayList<TileNode>();
		TileNode cur = null;
		alistWeird = generateAListWeird(maze);
		int c = 0;
		int x = 0, y = 0;
		int tempX = 0, tempY = 0;
		path.push(maze[0][0]);
		visited[0][0] = 1;
		while(!path.isEmpty()) {
			cur = path.pop();
			for(TileNode t : alistWeird.get(cur)) {
				if(visited[t.getX()][t.getY()] == 0) {
					temp.add(t);
				}
			}
			if(!temp.isEmpty()) {
				c = (int)(Math.random()*temp.size());
				path.push(cur);
				visited[temp.get(c).getX()][temp.get(c).getY()] = 1;
				x = cur.getX();
				y = cur.getY();
				tempX = temp.get(c).getX();
				tempY = temp.get(c).getY();
				
				if(tempX > x && tempY == y) {
					maze[x + 1][y].setStatus(false);
				}
				else if(tempX < x && tempY == y) {
					maze[x - 1][y].setStatus(false);
				}
				else if(tempX == x && tempY > y) {
					maze[x][y + 1].setStatus(false);
				}
				else {
					maze[x][y - 1].setStatus(false);
				}
				
				path.push(temp.get(c));
				temp.clear();
			}
		}
	}
	
	public boolean checkSurroundings(int i, int j) {
		for(int x = i-1; x <= i+1; x++) {
			for(int y = j-1; y <= j+1; y++) {
				try {
					if(!(x == i-1 && y != j) && !(x == i+1 && y != j)) {
						if(maze[x][y].getStatus()) {
							return false;
						}
					}
				}
				catch(Exception e) {
					
				}
			}
		}
		return true;
	}
	
	
	public void draw(Graphics g, int tileWidth, int tileHeight) {
		for(int i = 0; i < 101; i++) {
			for(int j = 0; j < 101; j++) {
				if(maze[i][j].getStatus()) {
					g.setColor(Color.black);
				}
				else {
					g.setColor(Color.white);
				}
				g.fillRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
				g.setColor(Color.yellow);
				g.drawRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
			}
		}
	}
	
	public static HashMap<TileNode, ArrayList<TileNode>> generateAList(TileNode[][] maze){
		HashMap<TileNode, ArrayList<TileNode>> ajlist = new HashMap<TileNode, ArrayList<TileNode>>();
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				
				ajlist.put(maze[i][j], new ArrayList<TileNode>());
				
				for(int x = i-1; x <= i+1; x++) {
					for(int y = j-1; y <= j+1; y++) {
						try {
							if(!(x == i-1 && y != j) && !(x == i+1 && y != j) ) {
								ajlist.get(maze[i][j]).add(maze[x][y]);
							}
						}
						catch(Exception e) {
							
						}
					}
				}
				
			}
		}
		
		return ajlist;
		
	}
	
	public static HashMap<TileNode, ArrayList<TileNode>> generateAListWeird(TileNode[][] maze){
		HashMap<TileNode, ArrayList<TileNode>> ajlist = new HashMap<TileNode, ArrayList<TileNode>>();
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				
				ajlist.put(maze[i][j], new ArrayList<TileNode>());
				
				for(int x = i-2; x <= i+2; x++) {
					for(int y = j-2; y <= j+2; y++) {
						try {
							if((x == i - 2 && y == j) || (x == i + 2 && y == j) || (x == i && y == j-2) || (x == i && y == j + 2)) {
								ajlist.get(maze[i][j]).add(maze[x][y]);
							}
						}
						catch(Exception e) {
							
						}
					}
				}
				
			}
		}
		
		return ajlist;
	}
}
