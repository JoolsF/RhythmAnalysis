package rhythm.analysis.model.suffixTree;

import java.util.List;


/**
 * InnerNode interface extends Node.  To be used by all nodes in the suffix tree apart from the root
 * @author Julian Fenner
 */
public interface InnerNode extends Node {
	
	/**
	 * Gets the full value of the string at this node from the root which is the value of the parent substring
	 * appended onto this node's substring value
	 * @return the string value of this node from the tree root 
	 */
	public String getfullString();
	
	/**
	 * Returns a list of indices at which the substring at this node occurs
	 * @return a list of indices
	 */
	public List<Integer> getIndices();
	
	/**
	 * Gets the index value of the node's substring field
	 * @return the node's substring index
	 */
	public int getSubstringIndex();
		
	/**
	 * Sets the node's substring field from the index position of the index argument
	 * @param start is the start position of the substring
	 */
	public void setSubstringByIndex(int start); 
	
	/**
	 * Sets the node's substring field
	 * @param index the node's substring index field
	 */
	public void setSubstringIndex(int index);
	
	/**
	 * Sets the parent of this node
	 * @param parent node
	 */
	public void setParent(Node parent);
	
	/**
	 * Gets the parents of this node
	 * @return parent node
	 */
	public Node getParent();

}
