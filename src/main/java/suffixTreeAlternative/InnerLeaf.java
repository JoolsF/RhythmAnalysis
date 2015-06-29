package suffixTreeAlternative;




//contains implementations of various methods common to NodeLead and NodeNonLead
public interface InnerLeaf {
	
	public default boolean isAPrefixOf(String string) {
		if(string.startsWith(this.getString()) && string.length() > this.getString().length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	public default boolean thisHasAPrefixOf(String string) {
		if(this.getString().startsWith(string) && this.getString().length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}

	public String getString();
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
