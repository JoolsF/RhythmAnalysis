package substringAlgorithms;

import java.util.List;

public interface Node {
	
	/**
	 * 
	 * @param subString
	 * @param subStringIndex
	 * @return true if substring added
	 */
	
	public void addSubString(String subString, int subStringIndex);
	public String getSubString();
	public void updateSubString(String subString, int subStringIndex);
	public List<Node> getChildren();
	public boolean isAPrefixOf(String string);
	public boolean hasAPrefixOf(String string);
	public void addChild(Node node);
	
}
