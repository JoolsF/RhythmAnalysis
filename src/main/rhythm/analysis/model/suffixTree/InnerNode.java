package rhythm.analysis.model.suffixTree;

import java.util.List;


/**
 *  Contains implementations of methods common to NodeLeaf and NodeNonLeaf
 *  TO DO - Consider adding the default methods to abstract class.
 *
 */
public interface InnerNode extends Node {
	public void setParent(Node parent);
	public Node getParent();
	public String getfullString();
	public List<Integer> getIndices();
	public int getStringIndex();
		
	public void setSubString(int start); 
	public void setStringIndex(int index);

}
