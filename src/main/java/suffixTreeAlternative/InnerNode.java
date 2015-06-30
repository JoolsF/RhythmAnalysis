package suffixTreeAlternative;




//contains implementations of methods common to NodeLead and NodeNonLead
public interface InnerNode extends Node {
	
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
	
	

	public String getString();
	public void setSubString(int start);
	/**
	 * i.e if getString() returns a and arg is abab the return is bab
	 * @param string
	 * @return
	 */
	public default String removeNodeFromArg(String string){
		return string.substring(getString().length());
	}
	
	
	public default String getCommonPrefix(String string){
		if(this.getString().length() > string.length()){
			return this.getString().substring(0, string.length());
		} else {
			return string.substring(0, this.getString().length());
		}
	}
	
	
	
	
/*
 * public void addSubString(String subString, int subStringIndex);
	
	
	
	public void updateSubString(String subString, int subStringIndex);
	
	public void setSubString(String subString, int subStringIndex);
	
	public List<Node> getChildren();
	
	public int getSubStringIndex();
	
	public boolean thisIsAPrefixOf(String string);
	
	public boolean thisHasAPrefixOf(String string);
	
	public void addChild(Node node);
	
	public void printTree();
	
	public String getChildStrings();
	
	public String removePrefix(String subStringArg);
	
	public Map<String, List<Integer>> nodesToMap();

	void removePrefixFromThis(String prefixToRemove);

	public void prepend(String parentSuffix);

	public void updateSubString(String subStringToAdd);
 */
	
	
	
	
}
