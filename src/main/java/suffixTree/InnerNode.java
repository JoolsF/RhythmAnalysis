package suffixTree;




//contains implementations of methods common to NodeLead and NodeNonLead
public interface InnerNode extends Node {
	
	//TO IMPLEMENT
	public String getString();
	public void setSubString(int start);
	
	
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
}
