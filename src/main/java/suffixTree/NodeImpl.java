package suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Julian Fenner
 * 
 *
 */

/*
 * TO REFACTOR
 * Split node into three classes, Root, Non-Leaf and Leaf
 * 
 * Decompose addSubstring method so for better testability and readability
 * 
 * Streamline interface, is too "fat".  Will need to make more methods private
 * Make prefix method and anything to do with implementation of the addsubstring() method 'private'.  
 * This will require children to be 'visited' rather than checked from parent ie. else if(child.thisHasAPrefixOf(subStringToAdd))
 * Implement logging to trace tree creation
 * http://tutorials.jenkov.com/java-logging/logger.html
 */
public class NodeImpl implements Node{
	private List<Node> children;
	private String subStringField;
	private Integer subStringIndex;
	
	//CONSTRUCTOR
	public NodeImpl(String subStringToAdd, int subStringIndex){
		this.subStringField = subStringToAdd;
		this.subStringIndex = subStringIndex;
		children = new ArrayList<Node>();
	}
	
	//GETTERS
	@Override
	public String getSubString() {
		return this.subStringField;
	}
	
	@Override
	public List<Node> getChildren() {
		return this.children;
	}

	
	@Override
	public String getChildStrings() {
		String childStr = "";
		for(Node next: children){
			childStr += next.getSubString() + " " + next.getSubStringIndex() +"\n";
		}
		return childStr;
	}
	
	@Override
	public int getSubStringIndex() {
		return this.subStringIndex;
	}
	
	
	//SETTERS
	@Override
	public void setSubString(String subString, int subStringIndex) {
		//TO DO - Remove debug method
		printNodeCreationStatus("Setting substring to ", subString, subStringIndex);
	
		this.subStringField = subString;
		this.subStringIndex = subStringIndex;	
	}
		
	@Override
	public void updateSubString(String subString, int subStringIndex) {
		//TO DO - Remove debug method
		printNodeCreationStatus("Updating substring to ", subString, subStringIndex);

		if(this.subStringField == "$") {
			this.subStringField = subString;
			this.subStringIndex = subStringIndex;
		} else {
		this.subStringField += subString;
		this.subStringIndex = subStringIndex;
		}
	}
	
	//TREE UPDATES
	@Override
	/**
	 * Whenever child the new subString is checked to see if it a terminating value $, if it is placed at the end
	 */
	public void addChild(Node node) {
		//TO DO - Remove debug method
		printNodeCreationStatus("Adding child ", node.getSubString(), node.getSubStringIndex());
		
		if(node.getSubString() != "$"){
			this.children.add(0,node);	
		} else {
			this.children.add(this.children.size(),node);
		}
	}
	/**
	 * Method for adding new subString to tree
	 * @param subStringToAdd
	 * @param subStringIndex
	 */
	@Override
	public void addSubString(String subStringToAdd, int subStringIndex){
	for(Node child: children) {
		//TO DO - Remove debug method
		//printNodeCreationStatus("Substring to add", subStringToAdd, subStringIndex);
	
		
		/* 
		 * TO DO - Refactor next block as too complicated and potentially buggy.
		 * Consider moving to method so it can be tested properly 
		 * To block deal with the case where the subString to add is an exact match of the node's subString
 		* Cases
  		* 1. The node has no children 
		*  ??? CHECK THIS FULLY
		* 2. The node has children
		* 	a. is has a child with a terminating "$" value
		* 		update this index value of this node to the new index
		*   b. there is no child with terminating "$" value
		*   	crate a new child node with "$" value and the new index
		This needs refactoring
		*/
		// TRY COMMENTING THIS BLOCK AND OBSERVE WHAT HAPPENS WHEN YOU ADD "aaa"
		if(child.getSubString().equals(subStringToAdd) && ! (subStringToAdd.equals("$"))){
				//TO DO - Remove line.  For debugging
				System.out.println("IN PROBLEM AREA------>");
				if(! (child.getChildren().isEmpty())){
					for(Node next: child.getChildren()){
						if(next.getSubString().equals("$")){
							next.updateSubString("$", subStringIndex);
							return;
						}
					}
					//if previous symbol has matched and there are no terminating symbols
/**/				child.addChild(new NodeImpl("$", subStringIndex));
					return;
				}
		}else if (child.getSubString().equals("$")){ 
			//	BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			//TO DO - UPDATE INDEX TOO FOR SAFETY?
			child.setSubString(subStringToAdd, subStringIndex);
			return;	
		} else if(child.thisHasAPrefixOf(subStringToAdd)) {
			//  3 CASES HERE?
			// 1  child's prefix is the substring to add and it is a LEAF NODE
			// 2  child's prefix is the substring to add and it is a NON LEAF NODE where one of the substring is a prefix of one of the children
			// 3  RECURSIVE CASE??
			
			String prefix =  child.getSubString().substring(0, subStringToAdd.length());
			String suffix = child.getSubString().substring(subStringToAdd.length(), child.getSubString().length());
System.out.println("DEBUG");
/**/		child.addChild(new NodeImpl(suffix, child.getSubStringIndex()));
			child.setSubString(prefix, -1);
/**/		child.addChild(new NodeImpl("$", subStringIndex)); 
			//	
			return;	
			}else if(child.thisIsAPrefixOf(subStringToAdd)){
				if(child.getChildren().isEmpty()) {
					//BASE CASE. child is a prefix of substring to add and there's no more children to traverse
					child.setSubString(subStringToAdd, subStringIndex);
					return;
				} else {	
					//RECURSIVE CASE.  child is a prefix of substring but there are more children to traverse
//CHECK IF ANY $ VALUES AT THIS POINT AND UPDATE THEM
						child.addSubString(child.removePrefix(subStringToAdd), subStringIndex);		
						return;
					}	
			} 
		} 
		//FINAL CASE - Outside of loop 
		//If no cases have been matched the method (ie. there will be no return) then by definition we must create a new child node		
		addChild(new NodeImpl(subStringToAdd, subStringIndex));
	}
	
	
	//Substring helper functions
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
	public void printTree(){
		Iterator<Node> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			System.out.println(" NODE: " + element.getSubString() + " (" + element.getSubStringIndex() + ")");
			element.printTree();
		}
	}

	@Override
	public Map<String, List<Integer>> nodesToMap() {
		Map<String, List<Integer>> nodeMap = new TreeMap<String, List<Integer>>();
		return nodesToMapHelper(nodeMap);
	}
	
	/**
	 *  Helper method to allow map to passed around as argument recursively
	 */
	//TO DO - Add exception if there are duplicate values in list value for given key.  
	// Indicates problem with construction of tree
	private Map<String, List<Integer>> nodesToMapHelper(Map<String, List<Integer>> accMap){	
		Iterator<Node> itr = children.iterator();
		while(itr.hasNext()){
			
			// TO DO, refactor so casting isn't used here. Consider functional approach
			 	
			NodeImpl currentNode = (NodeImpl) itr.next();
			String key = currentNode.getSubString();
			int value = currentNode.getSubStringIndex();
			
			if(accMap.get(key) == null) {
				accMap.put(key, new ArrayList<Integer>());	
			}
			
			accMap.get(key).add(value);
			currentNode.nodesToMapHelper(accMap);
		}
			return accMap;	
		
	}

	
	//TO DO - Remove and replace function with logger when refactoring.  Used to print trace of creational methods in tree
	private void printNodeCreationStatus(String action, String subString, int subStringIndex){
		System.out.println(action + " " + subString + " (" + subStringIndex + ")" + "\n"
					+ "Current value: " + this.subStringField + " (" + this.subStringIndex + ")" + "\n"
					+ "Child values: " + this.getChildStrings() + "\n");
	}
	
}
