package rhythm.analysis.model.suffixTree;

import java.util.List;

/**
 * Abstract class implementing InnerNode interface which in turn extends Node interface.
 * Since many method implementations are common to both non-leaf and leaf innerNodes they are
 * given here to avoid code-duplication.
 * InnerNode classes should extend this class rather than implement InnerNode. 
 * @author Julian Fenner
 */
public abstract class InnerNodeAbstract implements InnerNode {
	
	/*-----------------------------------------------------------------------------------------
	 * InnerNode checking methods
	 *----------------------------------------------------------------------------------------*/
	
	/**
	 * Checks if this node's substring field is a prefix of the string argument
	 * @param string argument to be checked against
	 * @return true if node is prefix of the string argument else false
	 */
	public boolean nodeIsAPrefixOf(String string) {
		if(string.startsWith(this.getSubstring()) && string.length() > this.getSubstring().length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Checks if the string argument is a prefix of this node's substring field
	 * @param string argument to be checked against
	 * @return true if string argument is a prefix of this node's substring field else false
	 */
	public boolean nodeHasAPrefixOf(String string) {
		if(this.getSubstring().startsWith(string) && this.getSubstring().length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Checks if this node needs to be split.
	 * @return true if this node has "$" sibling and total number of siblings are greater than 2 else false
	 */
	public boolean needToSplitNode(){
		// TO DO - Rethink right side of OR below.  
		// This deals with case where node's parent is root.	
		if ((getLastSiblingValue().equals("$") && this.getSiblings().size() > 2) || 
			  this.getParent() instanceof NodeRoot){			
			return true;
		}else {
			return false;
		}				
	}

	
	/*-----------------------------------------------------------------------------------------
	 * InnerNode modification methods
	 *----------------------------------------------------------------------------------------*/

	/**
	 * Takes a string argument and returns a substring of it.  The starting substring index value is
	 * this node's substring length i.e if this node's substring field is a and the string argument 
	 * is abab then bab is returned
	 * @param string to be modified
	 * @return the modified string
	 */
	public String removeNodeFromArg(String string){
		return string.substring(getSubstring().length());
	}
	
	
	/**
	 * Returns a substring of this node's substring field. The starting substring index value is the
	 * length of the string argument i.e if this node's substring field is abab and the string argument 
	 * is a then bab is returned
	 * @param the string to remove from the node's substring field
	 * @return the modified substring
	 */
	public String removeArgFromNode(String string){
		return this.getSubstring().substring(string.length());
	}
		
	/**
	 * Compares the string argument with the node's substring value and checks which is longer
	 * If the node's substring is longer it returns the node's substring up to the length of the string argument
	 * If the string argument is longer it returns this up to the length of the node's substring
	 * @param the string to compare against the node's substring field
	 * @return the modified substring
	 */
	public String getCommonPrefix(String string){
		if(this.getSubstring().length() > string.length()){
			return this.getSubstring().substring(0, string.length());
		} else {
			return string.substring(0, this.getSubstring().length());
		}
	}	
	
	/**
	 * Shortens this node's substring value by the length of the string argument and
	 * moves this removed substring's prefix up to the parent node
	 * @param string to be moved
	 */
	public void movePrefixUp(String string){	
		//TO DO - Fix this as have to cast currently. Class cast exception causing bug here
		InnerNode parent = (InnerNode) this.getParent();
		parent.setSubstring(parent.getSubstring() + this.getCommonPrefix(string));
		this.setSubstringByIndex(string.length());	
	}
	
		
	/*-----------------------------------------------------------------------------------------
	 * Child methods
	 *----------------------------------------------------------------------------------------*/
	
	/**
	 * Checks if this node has a child with a substring value with the same first letter as the string argument
	 * @param the string to be checked
	 * @return true if the string to be checked shares a first letter with any child node's substring field else false
	 */
	public boolean hasChildWithSameFirstLetter(String string){
		char charToCheck[] = string.toCharArray();
		for(InnerNode next: this.getChildren()){
			if(next.getSubstring().toCharArray()[0] == charToCheck[0]
					&& charToCheck[0] != charToCheck[1]
							){
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * Gets the values of this node's children
	 * @return a string representing the substring values and indices of this node's children
	 */
	public String getChildValues(){
		String childValues = "";
		//TO DO - Refactor so that guard condition is not needed for LeafNode
		if(this.getChildren() != null) {
			for(InnerNode next: this.getChildren()){
				childValues += next.getSubstring() + "(" +next.getSubstringIndex()+")  - ";
				}
		}
		return childValues;
	}
	
	/**
	 * Return the last child in this node's list of child nodes.
	 * @return the last node in the list of child nodes
	 */
	public InnerNode getLastChild(){
		return this.getChildren().get(this.getChildren().size()-1);
	}
	
	/**
	 * Gets the substring value of the last child in the list 
	 * @return the substring value of the last child in the list
	 */
	public String getLastSiblingValue(){
		return this.getParent().getChildren().get(this.getParent().getChildren().size()-1).getSubstring();
	}
	/**
	 * Gets the substring value of this node's last sibling in the parent's list 
	 * @return the substring value of the last sibling in the parent's list
	 */
	public InnerNode getLastSibling(){
		return this.getParent().getChildren().get(this.getParent().getChildren().size()-1);
	}
	
	/**
	 * Gets a list of this node's siblings
	 * @return a list of this node's siblings
	 */
	public List<InnerNode> getSiblings(){
		return this.getParent().getChildren();
	}
	
	/**
	 * Removes any % children from parent (if parent not root)
	 */
	public void remove$Children(){
		if(this.getLastSiblingValue().equals("$")){
			this.getParent().removeChild(getLastSibling());
			}
		}
	
	
	/*-----------------------------------------------------------------------------------------
	 * Default - Debug method
	 *----------------------------------------------------------------------------------------*/
	public void debugTrace(String location, String str, int index){		
		System.out.println("	*******************");
		System.out.println("	Location: " + location + " " + this.getSubstring() + "(" +this.getSubstringIndex() + ")");
		System.out.println("	Child values: " + getChildValues() );
		System.out.println("	Node type: " + this.getClass());
		System.out.println("	String to add: " + str + "(" + index +")");
		System.out.println();
		System.out.println();
	}

}
