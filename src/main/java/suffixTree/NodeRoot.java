package suffixTree;

import Node;
import NodeImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


//should be singleton
//shouldn't have getParent and addchild, rethink
public class NodeRoot implements Node {

	//to do change to private
	public List<InnerNode> children;
		
		public NodeRoot(){
			this.children = new ArrayList<InnerNode>();
		}
		
	/*CASES
	* 1 no children create new leaf node
	* 2 children
	*	a  str is not a "prefix / doesnt match" any children
	*		last child has $ value then str value give to to this child
	*	b still no matches then create new leaf node (only going to happen to $ after first iteration
	*/		
	@Override
	public boolean addString(String string, int index) {
		if (this.children.isEmpty()){
			this.children.add(new NodeLeaf(string, index, this));
			return true;
		} else{
			for(Node child: children){
				//for loop correct here?
				if(child.addString(string, index)){
					
					return true;
				}
			}
		}
		//i.e no matches found
		addChildLeaf(string, index);
		return true;
		
	}

	@Override
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode) {
		this.children.remove(nodeToDelete);
		if(replacementNode.getString().equals("$")){
			this.children.add(this.children.size(),replacementNode);	
		} else {
			this.children.add(0,replacementNode);
		}	
	}
	

	private void addChildLeaf(String string, int index) {
		InnerNode child = new NodeLeaf(string, index, this);
		if(string.equals("$")){
			this.children.add(this.children.size(),child);	
		} else {
			
			this.children.add(0,child);
		}		
	}

	@Override
	public void printTree() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			//System.out.println(" NODE: " + element.getSubString() + " (" + element.getSubStringIndex() + ") -> children: " + element.getChildStrings());
			element.printTree();
		}
		
	}
	
	//
	public Map<String, List<Integer>> nodesToMap() {
		Map<String, List<Integer>> nodeMap = new TreeMap<String, List<Integer>>();
		return nodesToMapHelper(nodeMap);
	}
	
	/**
	 *  Helper method to allow map to passed around as argument recursively
	 */
	//TO DO - Add exception if there are duplicate values in list value for given key.  
	// Indicates problem with construction of tree
	private Map<String, List<Integer>> nodesToMapHelper(Map<String, List<Integer>> accMap){	
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			
			// TO DO, refactor so casting isn't used here. Consider functional approach
			 	
			InnerNode currentNode = itr.next();
			String key = currentNode.getString();
			int value = currentNode.getStringIndex();
			
			if(accMap.get(key) == null) {
				accMap.put(key, new ArrayList<Integer>());	
			}
			
			accMap.get(key).add(value);
			currentNode.nodesToMapHelper(accMap);
		}
			return accMap;	
		
	}
	
	
	
}
