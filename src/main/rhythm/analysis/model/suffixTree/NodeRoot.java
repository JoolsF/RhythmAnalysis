package rhythm.analysis.model.suffixTree;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/*
 * TO DO
 * 1. Turn into singleton, can only be 1 root
 * 2. Shouldn't have getParent and addchild. Rethink interface etc.
 */

public class NodeRoot implements Node {
	
	private List<InnerNode> children;
	private List<Integer> indices;
	
	public NodeRoot(){
		this.children = new ArrayList<InnerNode>();
		indices = new ArrayList<Integer>();
	}
		
	/*CASES
	* 1 No children - create new leaf node
	* 2 Has children
	*	a  str is not a prefix of any children and last child has $ value. $ child takes value of string
	*	b  else create new leaf node.
	*/		
	@Override
	public boolean addString(String string, int index) {
		if (this.children.isEmpty()){		
//			debugTrace("	Children empty.  Creating node ", string, index);
			this.children.add(new NodeLeaf(string, index, this));
			return true;
		} else{
			for(Node child: children){
				if(child.addString(string, index)){
					return true;
				}
			}
		}
//		debugTrace("	No matches found. Creating node ", string, index);	
		addChildLeaf(string, index);
		return true;
	}

	/*-----------------------------------------------------------------------------------------
	 * Tree analysis and post-processing methods
	 *----------------------------------------------------------------------------------------*/
	
	@Override
	public List<Integer> processTree(String str) {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			InnerNode element = itr.next();
			indices.addAll(element.processTree(str));
		}		
		return this.indices;
	}

	
	
	@Override
	public void printTree() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			InnerNode element = itr.next();
			element.printTree();
		}	
	}
	
	

	/*-----------------------------------------------------------------------------------------
	 * Child methods
	 *----------------------------------------------------------------------------------------*/
	
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
	public void addChild(InnerNode child) {
		if(child.getString().equals("$")){
			this.children.add(this.children.size(),child);	
		} else {	
			this.children.add(0,child);
		}			
	}

	@Override
	public void removeChild(InnerNode child) {
		this.children.remove(child);	
	}
	
	
	@Override
	public void addChildren(List<InnerNode> children) {
		for(InnerNode next: children){
			next.setParent(this);
			
			if(next.getString().equals("$")){
				this.children.add(this.children.size(),next);	
			} else {
				this.children.add(0,next);
			}				
		}
	}
	
	private String getChildValues(){
		String childValues = "";
		//guard condition needed for LeafNode
		if(this.getChildren() != null) {
			for(InnerNode next: this.getChildren()){
				childValues += next.getString() + "(" +next.getStringIndex()+")  - ";
				}
		}
		return childValues;	
	}

	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	
	// TO DO - Incorporate this into rhythm controller method add string.  String should be stored in root not controller.
	
	@Override
	public String getString() {
		//TO DO - This should return whole string
		// Currently being used to check type of string to avoid class cast exception in InnerNode interface.  Code smell, bad OO design.  Rethink.
		return "ROOT";
	}
	
	//Refactor interfaces so this isn't needed
	@Override
	public void setString(String str) {
		//
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}

	
	private void debugTrace(String location, String str, int index){		
		System.out.println("	*******************");
		System.out.println("	Location: " + location + " " + this.getString());
		System.out.println("	Child values: " + this.getChildValues() );
		System.out.println("	Node type: " + this.getClass());
		System.out.println("	String to add: " + str + "(" + index +")");
		System.out.println();
		System.out.println();
	}
	
}
