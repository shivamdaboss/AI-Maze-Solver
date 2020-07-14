
public class BinaryHeap {
	private TileNode [] arr;
	private int size;
	private boolean littleG;
	BinaryHeap(int size, boolean littleG){
		arr = new TileNode[size];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}
		this.littleG = littleG; 
		this.size = 0;
	}
	
	public void push(TileNode t) {
		if(size >= arr.length - 1) {
			arr = expandArray(arr);
		}
		arr[size] = t;
		size++;
		//sift up
		int i = size - 1;
		TileNode par = null;
		int c = 0;
		while(i > 0){
			try {
				par = arr[(i - 1) / 2];
				if(littleG)
					c = par.compareToLittleG(arr[i]);
				else
					c = par.compareToBigG(arr[i]);
				if(c == -1) {
					arr[(i - 1) / 2] = arr[i];
					arr[i] = par;
				}
				else {
					break;
				}
			}
			catch(Exception e) {
				break;
			}
			i = (i - 1) / 2;
		}
	}
	
	public TileNode pop() {
		TileNode retVal = arr[0];
		arr[0] = arr[size - 1];
		arr[size - 1] = null;
		size--;
		//this.print();
		//sift down 
		TileNode lchil = null, rchil = null;
		int i = 0;
		int c = 0;
		while(i < size) {
			try {
				lchil = arr[(2*i) + 1];
				rchil = arr[(2*i) + 2];
				if(lchil == null && rchil == null) {
					break;
				}
				//System.out.println(lchil.getF() + " " + rchil.getF() + " " + i);
				
				//first try left
				c = arr[i].compareTo(littleG, lchil);
				if(c == 1 || c == 0) { // head is smaller than or equal to left child
					arr[i].compareTo(littleG, rchil);
					if(c == 1 || c == 0) { // head is smaller than or equal to both children
						break;
					}
					else if(c == -1) { // head is smaller than left child but bigger than right child
						arr[(2*i) + 2] = arr[i];
						arr[i] = rchil;
						i = (2*i) + 2;
					}
				}
				else { // head is bigger than left child
					c = arr[i].compareTo(littleG, rchil);
					if(c == 1 || c == 0) { // head is bigger than left child but smaller than the right child
						arr[(2*i)+1] = arr[i];
						arr[i] = lchil;
						i = (2*i)+1;
					}
					else { //head is bigger than both children
						c = lchil.compareTo(littleG, rchil);
						if(c == 1) { // left child is smaller or equal to right child;
							arr[(2*i)+1] = arr[i];
							arr[i] = lchil;
							i = (2*i)+1;
						}
						else if(c == 0) { //both right and left are the same size;
							if(((int)(Math.random()*100)) % 2 == 0) {
								arr[(2*i)+1] = arr[i];
								arr[i] = lchil;
								i = (2*i)+1;
							}
							else {
								arr[(2*i) + 2] = arr[i];
								arr[i] = rchil;
								i = (2*i) + 2;
							}
						}
						else { //right is smaller than left child
							arr[(2*i) + 2] = arr[i];
							arr[i] = rchil;
							i = (2*i) + 2;
						}
					}
				}
				
			}
			catch(Exception e) {
				//e.printStackTrace();
				break;
			}
		}
		return retVal;
	}
	
	private TileNode[] expandArray(TileNode[] arr) {
		TileNode[] temp = new TileNode[arr.length * 2];
		for(int i = 0; i < arr.length; i++) {
			temp[i] = arr[i];
		}
		for(int i = arr.length; i < temp.length; i++) {
			temp[i] = null;
		}
		return temp;
	}
	
	
	public void print() {
		for(int i = 0; i < size; i++) {
			System.out.println("at (" + arr[i].getX() + ", " + arr[i].getY() + ") " + arr[i].getF() + " " + arr[i].getG() + " " + arr[i].getH());
		}
	}
	
	public boolean isEmpty() {
		if(size == 0)
			return true;
		return false;
	}
	
	public int size() {
		return size;
	}
	
}
