package rhythm.analysis.model.suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeNonLeaf implements InnerNode {
	
	private String string;
	private int stringIndex;
	
	private String parentPrefix;
	private List<Integer> indices;
	
	private Node parent;
	private List<InnerNode> children;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructors
	 *----------------------------------------------------------------------------------------*/
	
	public NodeNonLeaf(String string, int stringIndex, Node parent){
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
		this.children = new ArrayList<InnerNode>();
		this.parentPrefix = "";
		this.indices = new ArrayList<Integer>();
	}
	
	public NodeNonLeaf(String string, int StringIndex, Node parent, List<InnerNode> children){
		this(string, StringIndex, parent);
		this.children = children;
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Add string
	 *----------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addString(String str, int index) {
				
		if(this.nodeHasAPrefixOf(str)){
//			debugTrace("Node has a a prefix of: ", str, index);
			//BASE CASE
			//TO DO check case if parent is root.
			if(this.needToSplitNode()){
				splitThisNode(str, index);
				return true;
			} else {
				//Move prefix onto parent and remove from this.
//				debugTrace("Move prefix onto parent and remove from this: ", str, index);
				movePrefixUp(str);				
				return true;	
			}
			
		} else if(this.string.equals(str)){
			//BASE CASE
			if(! this.needToSplitNode()  && ! (this.parent instanceof NodeRoot)){ // TO DO - refactor this.  Avoid checking class
				//i.e the current node matches str 100%
				//move the prefix to the parent and remove this node				
				this.getParent().setString(this.getParent().getString() + this.getCommonPrefix(str));
				this.getParent().addChildren(this.children);
				this.getParent().removeChild(this);
				return true;
			} else {
//				debugTrace("Node string, = str", str, index);
				//IF HAS A $ SIBLING THIS NEEDS TO BE REMOVED
				//IGNORE IF PARENT IS ROOT THOUGH
				this.remove$Children();
				if(children.get(children.size()-1).getString().equals("$")){
//					debugTrace("	Child has string of $, index changed only ", str, index);					
					children.get(children.size()-1).setStringIndex(index);				
					return true;
				} else {
//					debugTrace("	No child with $ value, creating leaf node ", str, index);			
					this.addChildLeaf("$", index);
					return true;
				}
			}
		} else if(this.nodeIsAPrefixOf(str)){
//			debugTrace("Node is a prefix of:",  str, index);
			//I.e the incoming string 'str' is a prefix of this then the next path must be chosen.  Each child will be iterated through
			for(InnerNode child: children){
				//goes through the children in turn, if a match is found will return true else will return false and continue
				if (child.addString(this.removeNodeFromArg(str), index)){
					return true;
				}
			}
			return false;	
		} else if(this.string.equals("$")){
			//BASE CASE
			//MEANS WE ARE AT LAST CHILD BECAUSE $ WHERE IT EXISTS WILL ALWAYS BE AT THE END
//			debugTrace("Node = $ (" + this.stringIndex + ")", str, index);
			this.string = string;
			this.stringIndex = index;
			return true;
		} else {
//			debugTrace("Nothing matched in NodeNonLeaf " + this.string + "(" + this.stringIndex  + ") Returning false", str, index);
			return false;
		}
		
	}	
	/**
	 * Private helper method for addString
	 */
	private void splitThisNode(String str, int index){
		remove$Children();
		String prefix = this.getCommonPrefix(str);  
		String suffix = this.removeArgFromNode(str);  
		List<InnerNode> newNodeChildren = new ArrayList<InnerNode>();
		NodeNonLeaf newNode = new NodeNonLeaf(prefix, -1, this.parent, newNodeChildren); 
		this.parent = newNode;
		this.string = suffix;
		newNodeChildren.add(this); 
		newNodeChildren.add(new NodeLeaf("$",index, newNode));
		newNode.parent.removeChild(this);
		newNode.parent.addChild(newNode);
	}
	

	/*-----------------------------------------------------------------------------------------
	 * Tree analysis and post-processing methods
	 *----------------------------------------------------------------------------------------*/
	
	/*
	 * 
	 * pass string value "down" 
	 * add accumulated indices at the end
	 */
	@Override
	public List<Integer> processTree(String str) {
		this.parentPrefix = str;
		this.indices.clear();
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			this.indices.addAll(element.processTree(this.parentPrefix + this.string));
		}
		return this.indices;
	}
	
	
	
	
	@Override
	public void printTree() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			element.printTree();
		}
		System.out.println("\n--------------------------------------------"
							+ "\n NODE: " + this.string + " (" + this.stringIndex + ") "
							+ "\n Type: " + this.getClass().toString()			
							+ "\n Children: " + this.getChildValues() +"\n"
							+ "\n Whole string " + this.parentPrefix + this.string
							+ "\n Indices: " + this.indices.toString()
							); 
							
	}
		
	/*-----------------------------------------------------------------------------------------
	 * Children methods
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
	
	
	@Override
	public void addChild(InnerNode child) {
		this.children.add(child);
		
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
	public void setParent(Node parent) {
		this.parent = parent;
		
	}
	
	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}
	@Override
	public Node getParent() {
		return this.parent;
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

	/*-----------------------------------------------------------------------------------------
	 * String getters and setters
	 *----------------------------------------------------------------------------------------*/
	
	@Override
	public String getString() {
		return this.string;
	}


	@Override
	public void setSubString(int start) {
		this.string = this.string.substring(start);
	}

	

	@Override
	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public void setString(String str) {
			this.string = str;
	}

	@Override
	public void setStringIndex(int index) {
		this.stringIndex = index;
		
	}

	@Override
	public String getfullString() {
		return this.parentPrefix + this.string;
	}

	@Override
	public List<Integer> getIndices() {
		return this.indices;
	}
	
}
