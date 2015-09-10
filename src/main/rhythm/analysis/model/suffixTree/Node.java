package rhythm.analysis.model.suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Node interface.  Every subclass of Node in the suffix tree implements this interface
 * Contains two default methods used for converting the suffix tree to a map or list representation
 * @author Julian Fenner
 */
public interface Node {
	

	/**
	 * Adds a substring to the node
	 * @param the substring to be added 
	 * @param the index position of the substring
	 * @return true if string successfully added to the node else returns false
	 */
	public boolean addSubstring(String str, int index);
	
	/**
	 * Gets the substring value of the node
	 * @return the node's substring value 
	 */
	public String getSubstring();
	
	/**
	 * Sets the substring value of the node
	 * @param the node's substring value 
	 */
	public void setSubstring(String str);
	
	/**
	 * Adds a child to Node
	 * @param a child node
	 */
	public void addChild(InnerNode child);
	
	/**
	 * Adds a list of children to the Node
	 * @param a list of child nodes 
	 */
	public void addChildren(List<InnerNode> children);
	
	/**
	 * Returns Node's children
	 * @return a list of nodes
	 */
	public List<InnerNode> getChildren();
	
	/**
	 * Removes a child from the node
	 * @param a child node
	 */
	public void removeChild(InnerNode child);
	
	/**
	 * Takes two arguments, a node to delete and a replacement node to replace it with
	 * @param the node to delete
	 * @param the node to replace the deleted node with
	 */
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode);
	
	/**
	 *  Prints the node's field
	 */
	public void printNode();
	
	/**
	 * Appends the child node with its substring value as the prefix.  The index values of the nodes are returned
	 * @param the string value of the node
	 * @return this list of indices at which the substring occurs
	 */
	public List<Integer> processTree(String str);

	
	
	/*-----------------------------------------------------------------------------------------
	 * Default methods
	 *----------------------------------------------------------------------------------------*/
	/**
	 * Default method for converting the suffix tree to a map representation.  The map's key is a substring
	 * and the value is the list of indices at which the substring occurs
	 * @param an empty map of String -> List<Integer> is passed as the argument allowing the return type to be parameterised.
	 * For example, if a TreeMap is given as the argument a specific key sort order can be defined.
	 * @return the String -> List<Integer> map of all substrings and indices in the suffix tree.
	 */
	public default Map<String, List<Integer>> nodesToMap(Map<String, List<Integer>> accMap){	
		
		Iterator<InnerNode> itr = getChildren().iterator();
		while(itr.hasNext()){			 	
			InnerNode currentNode = itr.next();
			String key = currentNode.getfullString();
			List<Integer> value = currentNode.getIndices();
		
			//If substring not already present in map then create new key val pair
			if(accMap.get(key) == null){ 
				accMap.put(key, new ArrayList<Integer>());		
			}
			accMap.get(key).addAll(value);
		
			currentNode.nodesToMap(accMap);
		}		
		//removes all $ values
		//accMap.remove("$");
		return accMap;					
	}
	
	
	
	/**
	 * Converts the nodeToMap data into a list of strings.
	 * @return a list of strings
	 */
	public default List<String> nodesToList(){
		Map<String, List<Integer>> nodeMap = nodesToMap(new TreeMap<String, List<Integer>>());
		List<String> nodeList = new ArrayList<String>();
		for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
    		nodeList.add((value.getKey() + ": " + value.getValue() +"\n"));
    	}
		return nodeList;
	}
	
	
	
	
	
}
