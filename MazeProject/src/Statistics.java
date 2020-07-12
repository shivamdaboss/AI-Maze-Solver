import java.util.ArrayList;

public class Statistics {
	
	private ArrayList<Integer> A_star_little_g, A_star_big_g, A_star_back, A_star_adap;
	
	Statistics(){
		A_star_little_g = new ArrayList<Integer>();
		A_star_big_g = new ArrayList<Integer>();
		A_star_back = new ArrayList<Integer>(); 
		A_star_adap = new ArrayList<Integer>();
	}
	
	public void addLittleG(int x) {
		A_star_little_g.add(x);
	}
	
	public void addBigG(int x) {
		A_star_big_g.add(x);
	}
	
	public void addBack(int x) {
		A_star_back.add(x);
	}
	
	public void addAdap(int x) {
		A_star_adap.add(x);
	}
	
	public void print() {
		System.out.println("A* (little g)\tA* (big G)\tA* Backwards\tA* Adaptive");
		System.out.println("-------------------------------------------------------------------------------");
		for(int i = 0; i < A_star_back.size(); i++) {
			System.out.println("" + A_star_little_g.get(i) + "\t\t" + A_star_big_g.get(i) + "\t\t" + A_star_back.get(i) + "\t\t" + A_star_adap.get(i));
		}
	}
	
	
	

}
