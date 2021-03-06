import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Player extends Thread{
	private int x, y;
	int expandedNodes = 0;
	private Maze m;
	private ArrayList<TileNode> prevClosed = null;
	
	//things needed for A* 
	TileNode[][] myView;
	HashMap<TileNode, ArrayList<TileNode>> alist;
	
	Player(int x, int y, Maze m){
		this.x = x;
		this.y = y;
		this.m = m;
		myView = new TileNode[101][101];
		for(int i = 0; i < 101; i++) {
			for(int j = 0; j < 101; j++) {
				myView[i][j] = new TileNode(false, i, j);
			}
		}
		alist = Maze.generateAList(myView);
		
	}
	
	public void move() {
		if(!this.isAlive()) {
			this.start();
		}
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void look() {
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				try {
					if(!(i == x-1 && j != y) && !(i == x+1 && j != y)) {
						myView[i][j].setStatus(m.maze[i][j].getStatus());
					}
				}
				catch(Exception e) {
					
				}
			}
		}
	}
	
	public void run() {	
		System.out.println("A* Forwards--> tie breaker: little G");
		boolean suc = true;
		Stack<TileNode> totalPath = new Stack<TileNode>();
		
		long beg = System.currentTimeMillis();
		long end;
		Stack<TileNode> path = A_star(myView[Game.start.getX()][Game.start.getY()], myView[Game.goal.getX()][Game.goal.getY()], true);
		TileNode next, cur = myView[Game.start.getX()][Game.start.getY()];
		totalPath.add(cur);
		while(x != Game.goal.getX() || y != Game.goal.getY()) {
			end = System.currentTimeMillis();
			if(end - beg > 100 || Runner.trials) {
				look();
				next = path.pop();
				if(next.getStatus()) {
					path = A_star(cur, myView[Game.goal.getX()][Game.goal.getY()], true);
					if(path == null) {
						suc = false;
						break;
					}
				}
				else {
					x = next.getX();
					y = next.getY();
					totalPath.push(next);
					cur = next;
				}
				beg = System.currentTimeMillis();
			}
		}
		Game.stat.addLittleG(expandedNodes);
		if(suc && !Runner.trials) {
			System.out.println("GOAL REACHED");
			System.out.println("Total Expanded Nodes:" + expandedNodes);
			System.out.println("Total Path Achieved: ");
			printPath(totalPath, true);
		}
		
		System.out.println("A* Forwards--> tie breaker: big G");

		expandedNodes = 0;
		x = Game.start.getX();
		y = Game.start.getY();
		clearMyView();
		path = A_star(myView[Game.start.getX()][Game.start.getY()], myView[Game.goal.getX()][Game.goal.getY()], false);
		next = null; 
		cur = myView[Game.start.getX()][Game.start.getY()];
		totalPath.clear();
		suc = true;
		totalPath.add(cur);
		
		try {
			this.sleep(1500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beg = System.currentTimeMillis();
		while(x != Game.goal.getX() || y != Game.goal.getY()) {
			end = System.currentTimeMillis();
			if(end - beg > 100 || Runner.trials) {
				look();
				next = path.pop();
				if(next.getStatus()) {
					path = A_star(cur, myView[Game.goal.getX()][Game.goal.getY()], false);
					if(path == null) {
						suc = false;
						break;
					}
				}
				else {
					x = next.getX();
					y = next.getY();
					totalPath.push(next);
					cur = next;
				}
				beg = System.currentTimeMillis();
			}
		}
		Game.stat.addBigG(expandedNodes);
		if(suc && !Runner.trials) {
			System.out.println("GOAL REACHED");
			System.out.println("Total Expanded Nodes:" + expandedNodes);
			System.out.println("Total Path Achieved: ");
			printPath(totalPath, true);
		}
		System.out.println("A* Backwards --> tie breaker: big G");

		expandedNodes = 0;
		x = Game.start.getX();
		y = Game.start.getY();
		clearMyView();
		Queue<TileNode> pathBack = A_star_back(myView[Game.goal.getX()][Game.goal.getY()], myView[Game.start.getX()][Game.start.getY()], false);
		next = null; 
		cur = myView[Game.start.getX()][Game.start.getY()];
		totalPath.clear();
		suc = true;
		totalPath.add(cur);
		
		try {
			this.sleep(1500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beg = System.currentTimeMillis();
		while(x != Game.goal.getX() || y != Game.goal.getY()) {
			end = System.currentTimeMillis();
			if(end - beg > 100 || Runner.trials) {
				look();
				next = pathBack.remove();
				if(next.getStatus()) {
					pathBack =  A_star_back(myView[Game.goal.getX()][Game.goal.getY()], cur, false);
					if(pathBack == null) {
						suc = false;
						break;
					}
				}
				else {
					x = next.getX();
					y = next.getY();
					totalPath.push(next);
					cur = next;
				}
				beg = System.currentTimeMillis();
			}
		}
		Game.stat.addBack(expandedNodes);
		if(suc && !Runner.trials) {
			System.out.println("GOAL REACHED");
			System.out.println("Total Expanded Nodes:" + expandedNodes);
			System.out.println("Total Path Achieved: ");
			printPath(totalPath, true);
		}
		
		System.out.println("A* Adaptive --> tie breaker: big G");

		expandedNodes = 0;
		x = Game.start.getX();
		y = Game.start.getY();
		clearMyView();
		path = A_star_adap(myView[Game.start.getX()][Game.start.getY()], myView[Game.goal.getX()][Game.goal.getY()], false);
		next = null; 
		cur = myView[Game.start.getX()][Game.start.getY()];
		totalPath.clear();
		suc = true;
		totalPath.add(cur);
		
		try {
			this.sleep(1500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beg = System.currentTimeMillis();
		while(x != Game.goal.getX() || y != Game.goal.getY()) {
			end = System.currentTimeMillis();
			if(end - beg > 100 || Runner.trials) {
				look();
				next = path.pop();
				if(next.getStatus()) {
					path =  A_star_adap(cur, myView[Game.goal.getX()][Game.goal.getY()], false);
					if(path == null) {
						suc = false;
						break;
					}
				}
				else {
					x = next.getX();
					y = next.getY();
					totalPath.push(next);
					cur = next;
				}
				beg = System.currentTimeMillis();
			}
		}
		Game.stat.addAdap(expandedNodes);
		if(suc && !Runner.trials) {
			System.out.println("GOAL REACHED");
			System.out.println("Total Expanded Nodes:" + expandedNodes);
			System.out.println("Total Path Achieved: ");
			printPath(totalPath, true);
		}
		Game.going = false;
		
		try {
			
			this.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Stack<TileNode> A_star(TileNode start, TileNode goal, boolean littleG){
		if(!Runner.trials)
			System.out.println("Performing A*...");
		long b = System.currentTimeMillis();
		BinaryHeap open = new BinaryHeap(30, littleG);
		HashSet<TileNode> closed = new HashSet<TileNode>();

		start.visit(0, calcH(start.getX(), start.getY(), goal));
		open.push(start);
		TileNode cur;
		TreeNode pathHead = new TreeNode(start, null);
		TreeNode currentNode = pathHead;
		while(!open.isEmpty() && !closed.contains(goal)) {
			cur = open.pop();
			if(closed.contains(cur)) {
				continue;
			}
			closed.add(cur);
			currentNode = TreeNode.search(pathHead, cur);
			for(TileNode t: alist.get(cur)) {
				if(!t.getStatus() && !closed.contains(t)) {
					t.visit(cur.getG() + 1, calcH(t.getX(), t.getY(), goal));
					open.push(t);
					currentNode.addChild(new TreeNode(t, currentNode));
				}
			}
		}
		expandedNodes += closed.size();
		if(open.isEmpty()) {
			if(!Runner.trials)
				System.out.println("PATH IS UNREACHABLE");
			return null;
		}
		else {
			Stack<TileNode> s = TreeNode.pathFinder(currentNode);
			long e = System.currentTimeMillis();
			if(!Runner.trials) {
				System.out.print("Path Calculated: ");
				printPath(s.clone(), false);
				long el = e - b;
				System.out.println("Total Time Elapsed: " + el + " milliseconds");
				System.out.println("Nodes Expanded:" + closed.size());
			}
			return s;
		}
	}
	
	public Queue<TileNode> A_star_back(TileNode start, TileNode goal, boolean littleG){
		if(!Runner.trials)
			System.out.println("Performing A*...");
		long b = System.currentTimeMillis();
		BinaryHeap open = new BinaryHeap(30, littleG);
		HashSet<TileNode> closed = new HashSet<TileNode>();

		start.visit(0, calcH(start.getX(), start.getY(), goal));
		open.push(start);
		TileNode cur;
		TreeNode pathHead = new TreeNode(start, null);
		TreeNode currentNode = pathHead;
		while(!open.isEmpty() && !closed.contains(goal)) {
			cur = open.pop();
			if(closed.contains(cur)) {
				continue;
			}
			closed.add(cur);
			currentNode = TreeNode.search(pathHead, cur);
			
			for(TileNode t: alist.get(cur)) {
				if(!t.getStatus() && !closed.contains(t)) {
					t.visit(cur.getG() + 1, calcH(t.getX(), t.getY(), goal));
					open.push(t);
					currentNode.addChild(new TreeNode(t, currentNode));
				}
			}
		}
		expandedNodes += closed.size();
		if(open.isEmpty()) {
			if(!Runner.trials)
				System.out.println("PATH IS UNREACHABLE");
			return null;
		}
		else {
			Queue<TileNode> s = TreeNode.pathFinderQ(currentNode);
			Queue<TileNode> temp = new LinkedList<TileNode>(s);
			long e = System.currentTimeMillis();
			if(!Runner.trials) {
				System.out.print("Path Calculated: ");
				printPath(temp, false);
				long el = e - b;
				System.out.println("Total Time Elapsed: " + el + " milliseconds");
				System.out.println("Nodes Expanded:" + closed.size());
			}
			return s;
		}
	}
	public Stack<TileNode> A_star_adap(TileNode start, TileNode goal, boolean littleG){
		if(!Runner.trials)
			System.out.println("Performing A*...");
		
		long b = System.currentTimeMillis();
		BinaryHeap open = new BinaryHeap(30, littleG);
		HashSet<TileNode> closed = new HashSet<TileNode>();
		if(prevClosed != null) {
			for(int i = 0; i < prevClosed.size(); i++) {
				prevClosed.get(i).setH(prevClosed.get(i).getF() - prevClosed.get(i).getG());
			}
		}
		start.visit(0, start.getH());
		open.push(start);
		TreeNode pathHead = new TreeNode(start, null);
		TreeNode currentNode = pathHead;
		TileNode cur;
		
		while(!open.isEmpty() && !closed.contains(goal)) {
			cur = open.pop();
			if(closed.contains(cur)) {
				continue;
			}
			
			closed.add(cur);
			currentNode = TreeNode.search(pathHead, cur);
			
			for(TileNode t: alist.get(cur)) {
				if(!t.getStatus() && !closed.contains(t)) {
					t.setG(cur.getG() + 1);
					if(prevClosed == null || !prevClosed.contains(t))
						t.setH(calcH(t.getX(), t.getY(), goal));
					t.setF(t.getG() + t.getH());				
					open.push(t);
					currentNode.addChild(new TreeNode(t, currentNode));
				}
			}
		}
		expandedNodes += closed.size();
		//System.out.println("Closed: " + closed);
		prevClosed = new ArrayList<TileNode>(closed);
		if(open.isEmpty()) {
			if(!Runner.trials)
				System.out.println("PATH IS UNREACHABLE");
			return null;
		}
		else {
			Stack<TileNode> s = TreeNode.pathFinder(currentNode);
			long e = System.currentTimeMillis();
			if(!Runner.trials) {
				System.out.print("Path Calculated: ");
				printPath(s.clone(), false);
				long el = e - b;
				System.out.println("Total Time Elapsed: " + el + " milliseconds");
				System.out.println("Nodes Expanded:" + closed.size());
			}
			return s;
		}
	}
	
	public int calcH(int x, int y, TileNode goal) {
		return Math.abs((101 - goal.getY()) - (101 -  y)) + Math.abs(goal.getX() - x);
	}
	
	
	public void printPath(Object s, boolean flip) {
		String str = "";
		if(s instanceof Stack) {
			while(!((Stack) s).isEmpty()) {
				if(flip)
					str = "Node " + ((Stack) s).pop() + " -> " + str;
				else 
					str = str + "Node " + ((Stack) s).pop() + " -> ";
			}
			str = str.substring(0, str.length() - 3);
			str = str + "|";
			System.out.println(str);
		}
		else if(s instanceof Queue) {
			while(!((Queue) s).isEmpty()) {
				if(flip)
					str = "Node " + ((Queue) s).remove() + " -> " + str;
				else 
					str = str + "Node " + ((Queue) s).remove() + " -> ";
			}
			str = str.substring(0, str.length() - 3);
			str = str + "|";
			System.out.println(str);
		}
	}
	
	public void clearMyView() {
		for(int i = 0; i < myView.length; i++) {
			for(int j = 0; j < myView[i].length; j++) {
				myView[i][j].setStatus(false);
				myView[i][j].visit(0, 0);
			}
		}
	}
}
 