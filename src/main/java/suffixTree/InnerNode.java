package suffixTree;

import java.util.List;




//contains implementations of methods common to NodeLead and NodeNonLead
public interface InnerNode extends Node {
	
	//TO IMPLEMENT
	public String getString();
	public int getStringIndex();
	public List<InnerNode> getChildren();
	
	public void setStringIndex(int index);
	public void setSubString(int start);
	public void setParent(Node parent);
	public Node getParent();
	
	//DEFAULT METHODS
	public default boolean nodeIsAPrefixOf(String string) {
		if(string.startsWith(this.getString()) && string.length() > this.getString().length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	public default boolean nodeHasAPrefixOf(String string) {
		if(this.getString().startsWith(string) && this.getString().length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	
	/**
	 * i.e if getString() returns a and arg is abab the return is bab
	 * @param string
	 * @return
	 */
	public default String removeNodeFromArg(String string){
		return string.substring(getString().length());
	}
	
	
	public default String removeArgFromNode(String string){
		return this.getString().substring(string.length());
	}
	
	
	public default String getCommonPrefix(String string){
		if(this.getString().length() > string.length()){
			return this.getString().substring(0, string.length());
		} else {
			return string.substring(0, this.getString().length());
		}
	}	
	
	public default boolean hasChildWithSameFirstLetter(String str){
		
		char charToCheck[] = str.toCharArray();
		for(InnerNode next: this.getChildren()){
			if(next.getString().toCharArray()[0] == charToCheck[0]
					&& charToCheck[0] != charToCheck[1]
							){
				return true;
			}
		}
		return false;
		
	}
	
	public default String getChildValues(){
		String childValues = "";
		//guard condition needed for LeafNode
		if(this.getChildren() != null) {
			for(InnerNode next: this.getChildren()){
				childValues += next.getString() + "(" +next.getStringIndex()+")  - ";
				}
		}
		return childValues;
	}
	
	public default InnerNode getLastChild(){
		return this.getChildren().get(this.getChildren().size()-1);
	}
	
	public default String getLastSiblingValue(){
		return this.getParent().getChildren().get(this.getParent().getChildren().size()-1).getString();
	}
	
	public default InnerNode getLastSibling(){
		return this.getParent().getChildren().get(this.getParent().getChildren().size()-1);
	}
	
	
	public default List<InnerNode> getSiblings(){
		return this.getParent().getChildren();
	}
	
	// returns true if the child has a non "$" sibling
	public default boolean needToSplitNode(){
		System.out.println("THIS VALUE " + this.getString());
		System.out.println("PARENT VALUE " + this.getString());
		if ((getLastSiblingValue().equals("$") && this.getSiblings().size() > 2) ||
		 (! getLastSiblingValue().equals("$") && this.getSiblings().size() > 1)) {
				
			return true;
		}else {
			return false;
		}
					
	}
	
	public default void movePrefixUp(String str){	
		//TO DO - fix this, have to cast.  What about if parent is Root etc
		InnerNode parent = (InnerNode) this.getParent();
		parent.setString(parent.getString() + this.getCommonPrefix(str));
		this.setSubString(str.length());	
	}
	
	public default void debugTrace(String location, String str, int index){
		
		System.out.println("	*******************");
		System.out.println("	Location: " + location + " " + this.getString() + "(" +this.getStringIndex() + ")");
		System.out.println("	Child values: " + getChildValues() );
		System.out.println("	Node type: " + this.getClass());
		System.out.println("	String to add: " + str + "(" + index +")");
		System.out.println();
		System.out.println();
	}
	
	public default void remove$Children(){
		//REMOVE ANY $ CHILDREN FROM PARENT (IF PARENT NOT ROOT)
		
			if(this.getLastSiblingValue().equals("$")){
				
				this.getParent().removeChild(getLastSibling());
			}
				
			
		}
	

	
	
}
