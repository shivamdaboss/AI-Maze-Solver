
public class Runner {
	public static boolean trials = true;
	public static void main(String[] args) {
		Game app;
		Statistics stat = new Statistics();
		if(trials) {
			for(int i = 0; i < 50; i++) {
				System.out.println("-----------------------------------------------------------------");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!Trial:"+i+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("-----------------------------------------------------------------");
				app = new Game(stat);
				
			}
		stat.print();
		}
		else {
			app = new Game(stat);
			stat.print();
		}
	}
}
