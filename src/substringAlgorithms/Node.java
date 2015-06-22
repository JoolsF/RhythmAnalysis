package substringAlgorithms;

import java.util.List;

public interface Node {
	
	public void addSubString(String subString, int subStringIndex);
	public String getSubString();
	public void updateSubString(String subString);
	public List<Node> getChildren();
	
}
