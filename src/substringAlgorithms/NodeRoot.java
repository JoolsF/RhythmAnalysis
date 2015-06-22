package substringAlgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Fenner
 * Root can have zero, one or more children.
 *
 */


public class NodeRoot implements Node{
	//String rootString;
	private List<NodeImpl> children;
	
	public void NodeRoot(){
		//TO DO, implement singleton pattern.  Only one root allowed
		children = new ArrayList<NodeImpl>();
	}
	

	public void addSubString(String subString, int subStringIndex){
	
		if(children.isEmpty()){
			children.add(new NodeImpl(subString,subStringIndex));
		} else {	
			for(NodeImpl child: children) {
				if (subString.startsWith(child.getSubString())){
					child.addSubString
					
					
					//child.updateSubString(subString);
					return; // i.e subString added
				} else if (child.getSubString().startsWith(subString)){
					//i.e the subString we are checking is a prefix of the child's subString
					// we will need to split the node here
					// TO IMPLEMENT
				}
					
			
				}
			}
			//i.e no subString is not a prefix of any child or no child is prefix of any subString
			children.add(new NodeImpl(subString,subStringIndex));
	}
	
}
