package substringAlgorithms;

/**
 * Has no children by definition
 * Has index
 * SHOULD ALWAYS END IN $
 */

import java.util.List;

public class NodeLeaf1 implements Node{

	private String subString;
	private int subStringIndex;
	
	public NodeLeaf1(String str, int subStringIndex){
		this.subString = subString;
		this.subStringIndex = subStringIndex;	
	}
	
	
	@Override
	public void addSubString(String substringToAdd, int subStringToAddIndex) {
		if (substringToAdd.startsWith(this.subString)){
			//checking if the substring in this leaf node is a prefix of the substringToAdd field.
			updateSubString(substringToAdd);
		} // TO DO else need to deal with case where we're at leaf node and the leaf node 
			
			
	}
		
	

	@Override
	public String getSubString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSubString(String subString) {
		this.subString += subString;
		
	}

	@Override
	public List<Node> getChildren() {
		return null;
	}

}
