
public class TileNode {
	
	private boolean blocked;
	private int x, y;
	private int f, g, h;
	
	
	public TileNode(boolean blocked, int x, int y) {
		this.blocked = blocked;
		this.x = x;
		this.y = y;
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}
	
	public boolean getStatus() {
		return blocked;
	}
	
	public void visit(int g, int h) {
		this.f = g + h;
		this.g = g;
		this.h = h;
	}
	
	public void setStatus(boolean blocked) {
		this.blocked = blocked;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getF() {
		return f;
	}
	
	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}
	
	public void setF(int x) {
		f = x;
	}
	
	public void setG(int x) {
		g = x;
	}
	
	public void setH(int x) {
		h = x;
	}

	public int compareToLittleG(TileNode t) {
		if(t == null) {
			return 1;
		}
		
		if(this.f == t.getF()) {
			if(this.g == t.getG()) {
				return 0;
			}
			else if(this.g < t.getG()) {
				return 1;
			}
			else if(this.g > t.getG()) {
				return -1;
			}
		}
		else if(this.f < t.getF()) {
			return 1;
		}
		else {
			return -1;
		}
		return -1;
	}
	
	public int compareToBigG(TileNode t) {
		if(t == null) {
			return 1;
		}
		if(this.f == t.getF()) {
			if(this.g == t.getG()) {
				return 0;
			}
			else if(this.g < t.getG()) {
				return -1;
			}
			else if(this.g > t.getG()) {
				return 1;
			}
		}
		else if(this.f < t.getF()) {
			return 1;
		}
		else {
			return -1;
		}
		return -1;
	}
	
	public int compareTo(boolean littleG, TileNode t) {
		if(littleG) {
			return this.compareToLittleG(t);
		}
		else {
			return this.compareToBigG(t);
		}
	}
	
	public String toString() {
		return "(" + x+ ", " + y + ") with (" + f + "," + g + "," + h + ")";
	}
}
