import java.util.ArrayList;
import java.util.Stack;

public class TreeNode {
	
	private TileNode data;
	private TreeNode parent;
	private ArrayList<TreeNode> children;
	
	TreeNode(TileNode data, TreeNode parent){
		this.data = data;
		this.parent = parent;
		children = new ArrayList<TreeNode>();
	}
	
	public void addChild(TreeNode t) {
		children.add(t);
	}
	
	public TileNode getData() {
		return data;
	}
	public TreeNode getParent() {
		return parent;
	}
	
	public ArrayList<TreeNode> getChildren(){
		return children;
	}
	
	public static TreeNode search(TreeNode head, TileNode target) {
		if(head.data.equals(target)) {
			return head;
		}
		for(int i = 0; i < head.getChildren().size(); i++) {
			if(head.getChildren().get(i).data.equals(target)) 
				return head.getChildren().get(i);
		}
		TreeNode temp;
		for(int i = 0; i < head.getChildren().size(); i++) {
			temp = search(head.getChildren().get(i), target);
			if(temp != null) 
				return temp;
		}
		return null;
	}
	
	public static Stack<TileNode> pathFinder(TreeNode goal){
		TreeNode cur = goal;
		Stack<TileNode> path = new Stack<TileNode>();
		while(cur.getParent() != null) {
			path.push(cur.getData());
			cur = cur.getParent();
			
		}
		return path;
	}
}
 