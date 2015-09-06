package rhythm.analysis.model.suffixTree;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NodeRoot class forms the root of the suffix tree.  Implements Node interface
 * @author Julian Fenner
 */


public class NodeRoot implements Node {
	
	private List<InnerNode> children;
	private List<Integer> indices;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
	public NodeRoot(){
		this.children = new ArrayList<InnerNode>();
		indices = new ArrayList<Integer>();
	}
		
	
	/*-----------------------------------------------------------------------------------------
	 * Add substring
	 *----------------------------------------------------------------------------------------*/
	
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean addSubstring(String string, int index) {
		if (this.children.isEmpty()){ /* Node has no children so a new leaf node must be created */
//			debugTrace("	Children empty.  Creating node ", string, index);
			this.children.add(new NodeLeaf(string, index, this));
			return true;
		} else{   /* Node has children therefore iterate through child nodes via their addSubstring */
			for(Node child: children){
				if(child.addSubstring(string, index)){
					return true;
				}
			}
		} 
		/* If method reaches this point no matches have been found in children therefore add new child leaf node*/
//		debugTrace("	No matches found. Creating node ", string, index);	
		addChildLeaf(string, index);
		return true;
	}

	/*-----------------------------------------------------------------------------------------
	 * Tree analysis and post-construction processing methods
	 *----------------------------------------------------------------------------------------*/
	
	/**
	 * @inheritDoc
	 */
	@Override
	public List<Integer> processTree(String str) {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			InnerNode element = itr.next();
			indices.addAll(element.processTree(str));
		}		
		return this.indices;
	}

	
	/**
	 * @inheritDoc
	 */
	@Override
	public void printNode() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			InnerNode element = itr.next();
			element.printNode();
		}	
	}
	
	

	/*-----------------------------------------------------------------------------------------
	 * Child methods
	 *----------------------------------------------------------------------------------------*/
	/**
	 * @inheritDoc
	 */
	@Override
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode) {
		this.children.remove(nodeToDelete);
		if(replacementNode.getSubstring().equals("$")){
			this.children.add(this.children.size(),replacementNode);	
		} else {
			this.children.add(0,replacementNode);
		}	
	}
	
	/**
	 * @inheritDoc
	 */	
	@Override
	public void addChild(InnerNode child) {
		if(child.getSubstring().equals("$")){
			this.children.add(this.children.size(),child);	
		} else {	
			this.children.add(0,child);
		}			
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public void removeChild(InnerNode child) {
		this.children.remove(child);	
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void addChildren(List<InnerNode> children) {
		for(InnerNode next: children){
			next.setParent(this);
			
			if(next.getSubstring().equals("$")){
				this.children.add(this.children.size(),next);	
			} else {
				this.children.add(0,next);
			}				
		}
	}
	/**
	 * @inheritDoc
	 */
	private void addChildLeaf(String string, int index) {
		InnerNode child = new NodeLeaf(string, index, this);
		if(string.equals("$")){
			this.children.add(this.children.size(),child);	
		} else {	
			this.children.add(0,child);
		}		
	}	
	
	private String getChildValues(){
		String childValues = "";
		if(this.getChildren() != null) { /*guard condition needed for LeafNode*/
			for(InnerNode next: this.getChildren()){
				childValues += next.getSubstring() + "(" +next.getSubstringIndex()+")  - ";
				}
		}
		return childValues;	
	}

	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/

	/**
	 * @inheritDoc
	 */
	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	// TO DO - Currently being used to check type of string to avoid class cast exception in 
	// InnerNode interface.  Re think to confirm to good OO principles 
	public String getSubstring() {
		return "ROOT";
	}

	/**
	 * @inheritDoc
	 */
	@Override
	//TO DO - Refactor interface so that this isn't needed.  Null implementation
	public void setSubstring(String str) {
		// TODO Auto-generated method stub
	}
	
	private void debugTrace(String location, String str, int index){		
		System.out.println("	*******************");
		System.out.println("	Location: " + location + " " + this.getSubstring());
		System.out.println("	Child values: " + this.getChildValues() );
		System.out.println("	Node type: " + this.getClass());
		System.out.println("	String to add: " + str + "(" + index +")");
		System.out.println();
		System.out.println();
	}	
}