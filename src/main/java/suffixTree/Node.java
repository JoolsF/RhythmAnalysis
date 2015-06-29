package suffixTree;
import java.util.List;
import java.util.Map;

public interface Node{
	
	/**
	 * 
	 * @param subString
	 * @param subStringIndex
	 * @return true if substring added
	 */
	
	public void addSubString(String subString, int subStringIndex);
	
	public String getSubString();
	
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

	
}
