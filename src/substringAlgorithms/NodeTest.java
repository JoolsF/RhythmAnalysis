package substringAlgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Fenner
 * Root can have zero, one or more children.
 *
 */


public class NodeTest implements Node{
	//TO DO CHANGE TO NODE INTERFACE
	private List<Node> children;
	private String subStringField;
	private Integer subStringIndex;
	
	public NodeTest(String subStringToAdd, int subStringIndex){
		//TO DO, implement singleton pattern.  Only one root allowed
		this.subStringField = subStringToAdd;
		this.subStringIndex = subStringIndex;
		children = new ArrayList<Node>();
	}
	

	public void addSubString(String subStringToAdd, int subStringIndex){
	
		if(children.isEmpty()){
			children.add(new NodeTest(subStringToAdd, subStringIndex));
		} else {	
			for(Node child: children) {
				
				if(child.isAPrefixOf(subStringToAdd)){
					//checking if the child's substring is a prefix of the substring field. i.e child's substring is a and substring is ab
					child.addSubString(subStringToAdd, subStringIndex);
					return;
				} else if(child.hasAPrefixOf(subStringToAdd)) {
				//checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1.
				//If yes then SPLIT	  
				//In other word the substring to add ends part of the way through the child's substring value.
				// in this case the node must be split
				// two cases one for lead and one for non leaf
				//
				//CASE FOR LEAF NODE
				//checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1 	
				/* This case turns a LEAF node where the substring to add is a prefix of this node's substring field value.  
				 * i.e
				 * 		   \
				 * 			\
				 * 			11 (2)  <- adding 1 to leaf
				 * 
				 * 			\
				 *           \
				 *            1
				 * 			 / \
				 * 			/   \
				 * 		null(3)  1(2)
				 * 		
				 * 
				 * 
				 * This requires 
				 * 1) var for matching prefix 
				 * 2) var for rest of string i.e suffix
				 * 3) adding a child leaf node which has the suffix as its value along with the index of the current node
				 * 4) current node 'converted' into non-leaf node by deleting its index (not applicable now) 
				 * 5) and setting its substring field to prefix
				 * 6) finally we need to give the other index of the prefix 
				 * 
				 * 
				 **/
					
				//1
				String prefix =  subStringField.substring(0, subStringToAdd.length());
				//2
				String suffix = subStringField.substring(subStringToAdd.length(), subStringField.length());
				//3 LEAF NODE
				child.addChild(new NodeTest(suffix, this.subStringIndex));
				//4
				this.subStringIndex = null;
				//5
				subStringField = prefix;
				//6 string is null to represent the equivalent of $ terminating symbol
				children.add(new NodeTest(null, subStringIndex)); 
				//	
					
				return;	
				}
				
				
			}
			// THIS POINT ONLY REACHED IF NOT MATCHES ON NODES.  RETURN SHOULD PREVENT THIS FROM BEING REACHED
			// TO CHECK THIS WORKS
			children.add(new NodeTest(subStringToAdd, subStringIndex));
		}
		
		
		
	}


	@Override
	//checks if this object's subString field is a prefix of param string
	public boolean isAPrefixOf(String string) {
		if(string.startsWith(this.subStringField) && string.length() > this.subStringField.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public boolean hasAPrefixOf(String string) {
		if(this.subStringField.startsWith(string) && this.subStringField.length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public String getSubString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateSubString(String subString) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Node> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addChild(Node node) {
		this.children.add(node);
	}


	


	
					


	
}
