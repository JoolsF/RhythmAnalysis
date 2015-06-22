package substringAlgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Fenner
 * Root can have zero, one or more children.
 *
 */


public class NodeRoot implements Node{
	private List<Node> children;
	private String subString;
	private int subStringIndex;
	
	public void NodeRoot(){
		//TO DO, implement singleton pattern.  Only one root allowed
		children = new ArrayList<Node>();
	}
	

	public void addSubString(String subString, int subStringIndex){
	
		if(children.isEmpty()){
			children.add(new NodeNonLeaf(subString + "$",subStringIndex)); //TO DO CHECK USE OF TERMINATING SYMBOL '$'
		} else {	
			for(Node child: children) {
				addSubString(subString, subStringIndex);
			}
			
		}
	}
					


	@Override
	public void updateSubString(String subString) {
		this.subString += subString;
		
	}


	@Override
	public String getSubString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Node> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
