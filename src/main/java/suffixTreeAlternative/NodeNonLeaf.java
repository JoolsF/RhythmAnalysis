package suffixTreeAlternative;

import java.util.ArrayList;
import java.util.List;

public class NodeNonLeaf implements Node {
	
	private String string;
	private int stringIndex;
	private Node parent;
	private List<Node> children;
	
	
	public NodeNonLeaf(String string, int StringIndex, Node parent){
		
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
		this.children = new ArrayList<Node>();
	}

	@Override
	public void addString(String str, int index) {
		// TODO Auto-generated method stub
		
	}


	
	

		

}
