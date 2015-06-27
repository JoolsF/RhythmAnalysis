package substringAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Julian Fenner
 * Root can have zero, one or more children.
 *
 */

/* NODE SPLIT LOGIC
 *checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1.
If yes then SPLIT	  
In other word the substring to add ends part of the way through the child's substring value.
 in this case the node must be split
 two cases one for lead and one for non leaf

CASE FOR LEAF NODE
checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1 	
 This case turns a LEAF node where the substring to add is a prefix of this node's substring field value.  
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


public class NodeImpl implements Node{
	//TO DO CHANGE TO NODE INTERFACE
	private List<Node> children;
	private String subStringField;
	private Integer subStringIndex;
	
	public NodeImpl(String subStringToAdd, int subStringIndex){
		//TO DO, implement singleton pattern.  Only one root allowed
		this.subStringField = subStringToAdd;
		this.subStringIndex = subStringIndex;
		children = new ArrayList<Node>();
	}
	
	//TO DELETE
	public void printLocation(String location, String childSubString, String subStringToAdd, int subStringIndex){
////		System.out.println("Location: " + location);
////		System.out.println("Child's subString: " + childSubString);
////		System.out.println("subStringToAdd: " + subStringToAdd);
////		System.out.println("subStringToAdd Index: " + subStringIndex);
////		System.out.println();
//		
		
	}
	
	
	//	if(child.getChildren().isEmpty()){
	//	System.out.println("--->>String Updated");
	//	this.updateSubString(subStringToAdd, subStringIndex);
	//	return;
	//}  else 

	
	
	public void addSubString(String subStringToAdd, int subStringIndex){
	for(Node child: children) {
		System.out.println("----> SS TO ADD " + subStringToAdd + " " + subStringIndex);
		System.out.println("----> Child SS " + child.getSubString());
		
		//TO DO NEEDS REFACTORING, TOO COMPLICATED!
		// TRY COMMENTING THIS BLOCK AND OBSERVE WHAT HAPPENS WHEN YOU ADD "aaa"
		if(child.getSubString().equals(subStringToAdd) && ! (subStringToAdd.equals("$"))){
				System.out.println("HERE IS THE PROBLEM!------>");
				if(! (child.getChildren().isEmpty())){
					for(Node next: child.getChildren()){
						if(next.getSubString().equals("$")){
							next.updateSubString("$", subStringIndex);
							return;
						}
					}
				}
		}else if (child.getSubString().equals("$")){ 
			System.out.println(":)");
			//	BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			//TO DO - UPDATE INDEX TOO FOR SAFETY?
			printLocation("childSS equals $", child.getSubString(), subStringToAdd, subStringIndex);
			child.setSubString(subStringToAdd, subStringIndex);
			return;	
		} else if(child.thisHasAPrefixOf(subStringToAdd)) {
			//TWO CASES HERE
			// 1 LEAF NODE
			// 2 NON LEAF NODE
			//
			printLocation("child has a prefix of", child.getSubString(), subStringToAdd, subStringIndex);

			//1
			String prefix =  child.getSubString().substring(0, subStringToAdd.length());
			//2
			String suffix = child.getSubString().substring(subStringToAdd.length(), child.getSubString().length());
			//3 LEAF NODE
			child.addChild(new NodeImpl(suffix, child.getSubStringIndex()));
			//4
			//TO DO - DEAL WITH NON LEAF NODES DIFFERENTLY NOT WITH NULL
			
			//5
			child.setSubString(prefix, -1); //TO DO CHECK -1
			//6 string is null to represent the equivalent of $ terminating symbol
			//TO DO - check first if these is already a terminating symbol $ here
			child.addChild(new NodeImpl("$", subStringIndex)); 
			//	
			return;	
			}else if(child.thisIsAPrefixOf(subStringToAdd)){
				printLocation("child is a prefix of", child.getSubString(), subStringToAdd, subStringIndex);
				if(child.getChildren().isEmpty()) {
					//BASE CASE. child is a prefix of substring to add and there's no more children to traverse
					child.setSubString(subStringToAdd, subStringIndex);
					return;
				} else {	
					//RECURSIVE CASE.  child is a prefix of substring but there are more children to traverse
						child.addSubString(child.removePrefix(subStringToAdd), subStringIndex);		
						return;
					}	
		} 
		} //`end of for loop
	    
	//else if (subStringIndex == children.size()){
		//BASE CASE 
		//We are checking the last element in the current substring and no other cases have been matched. There will be no return
		//printLocation("last element", child.getSubString(), subStringToAdd, subStringIndex);			
		addChild(new NodeImpl(subStringToAdd, subStringIndex));
		return;
		//}
	}
	
	
	/**
	 * throws exception if arg is shorter than subStringfield
	 * @param subStringArg
	 * @return
	 */
	@Override
	public String removePrefix(String subStringArg){
		return subStringArg.substring(this.subStringField.length());
	}
	

	@Override
	/**checks if this object's subString field is a prefix of param string.
	 * checks length as well to make sure they're not exact match
	 */
	public boolean thisIsAPrefixOf(String string) {
		if(string.startsWith(this.subStringField) && string.length() > this.subStringField.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Check if argument is a prefix of object's subString field 
	 * @param string
	 * @return
	 */
	@Override
	public boolean thisHasAPrefixOf(String string) {
		if(this.subStringField.startsWith(string) && this.subStringField.length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public String getSubString() {
		return this.subStringField;
	}


	@Override
	public void updateSubString(String subString, int subStringIndex) {
		System.out.println("updateSubString " + subString + " " + subStringIndex);
		System.out.println();
		
		if(this.subStringField == "$") {
			this.subStringField = subString;
			this.subStringIndex = subStringIndex;
		} else {
		this.subStringField += subString;
		this.subStringIndex = subStringIndex;
		}
	
	}


	@Override
	public List<Node> getChildren() {
		return this.children;
	}


	@Override
	/**
	 * Whenever child is added the collection is sorted to ensure any $ string are at the end
	 */
	public void addChild(Node node) {
		System.out.println("add child " + node.getSubString() + " " + node.getSubStringIndex());
		System.out.println("Adding child");
		System.out.println("This subString: " + this.subStringField + " " + this.subStringIndex);
		System.out.println("child subString: " + this.getChildStrings());
		System.out.println();
		
		if(node.getSubString() != "$"){
			this.children.add(0,node);	
		} else {
			this.children.add(this.children.size(),node);
		}
	}

	public void printTree(){
		
		
		for(Node child: children){
			if(child.getChildren().isEmpty()){
				System.out.println(child.getSubString() + " " + child.getSubStringIndex());
			} else {
				child.printTree();
			}
				
			}
	}
	
		
	


	

	@Override
	public int getSubStringIndex() {
		return this.subStringIndex;
	}


	@Override
	public void setSubString(String subString, int subStringIndex) {
		
		System.out.println("Setting substring " + subString + " " + subStringIndex);
		System.out.println("This subString: " + this.subStringField + " " + this.subStringIndex);
		System.out.println("child subString: " + this.getChildStrings());
		System.out.println();
		
		this.subStringField = subString;
		this.subStringIndex = subStringIndex;
		
	}

	@Override
	public String getChildStrings() {
		String childStr = "";
		for(Node next: children){
			childStr += next.getSubString() + " " + next.getSubStringIndex() +"\n";
		}
		return childStr;
	}
	
}
