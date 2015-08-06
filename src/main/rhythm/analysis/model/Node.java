package rhythm.analysis.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public interface Node {
	
	
	/*-----------------------------------------------------------------------------------------
	 * Abstract
	 *----------------------------------------------------------------------------------------*/
	public boolean addString(String str, int index); //returns true if string added successfully
	public String getString();
	public void setString(String str);
	
	public List<InnerNode> getChildren();
	public void addChild(InnerNode child);
	public void addChildren(List<InnerNode> children);
	public void removeChild(InnerNode child);
	
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode);
	
	public void printTree();
	
	/*-----------------------------------------------------------------------------------------
	 * Default - Output methods
	 *----------------------------------------------------------------------------------------*/
	
	//TO DO - Add exception if there are duplicate values in list value for given key.  
	// Indicates problem with construction of tree
	public default Map<String, List<Integer>> nodesToMap(Map<String, List<Integer>> accMap){	
		Iterator<InnerNode> itr = getChildren().iterator();
		while(itr.hasNext()){			 	
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
	
	
	public default List<String> nodesToList(){
		Map<String, List<Integer>> nodeMap = nodesToMap(new TreeMap<String, List<Integer>>());
		List<String> nodeList = new ArrayList<String>();
		for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
    		nodeList.add((value.getKey() + ": " + value.getValue() +"\n"));
    	}
		return nodeList;
	}
	
	
	
}
