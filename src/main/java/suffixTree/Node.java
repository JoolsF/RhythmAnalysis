package suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Node {

	public String getString();
	public boolean addString(String str, int index); //returns true if string added successfully
	public void setString(String str);
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode);
	public void printTree();
	public List<InnerNode> getChildren();
	public void addChild(InnerNode child);
	public void removeChild(InnerNode child);

	/**
	 *  
	 */
	//TO DO - Add exception if there are duplicate values in list value for given key.  
	// Indicates problem with construction of tree
	public default Map<String, List<Integer>> nodesToMap(Map<String, List<Integer>> accMap){	
		Iterator<InnerNode> itr = getChildren().iterator();
		while(itr.hasNext()){
			
			// TO DO, refactor so casting isn't used here. Consider functional approach
			 	
			InnerNode currentNode = itr.next();
			String key = currentNode.getString();
			int value = currentNode.getStringIndex();
			
			if(accMap.get(key) == null) {
				accMap.put(key, new ArrayList<Integer>());	
			}
			
			accMap.get(key).add(value);
			currentNode.nodesToMap(accMap);
		}
			return accMap;	
		
	}
	
	
}
