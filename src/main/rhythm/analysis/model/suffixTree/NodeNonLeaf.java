package rhythm.analysis.model.suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * NodeNonLeaf class extends InnerNodeAbstract.  Represents all non-leaf nodes in the suffix tree apart from the root
 * @author Julian Fenner
 */


public class NodeNonLeaf extends InnerNodeAbstract {
	
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
	 * Add substring
	 *----------------------------------------------------------------------------------------*/
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public boolean addSubstring(String str, int index) {
				
		if(this.nodeHasAPrefixOf(str)){
//			debugTrace("Node has a a prefix of: ", str, index);
			if(this.needToSplitNode()){
				splitThisNode(str, index);
				return true;
			} else { /* Move prefix onto parent and remove from this.*/
//				debugTrace("Move prefix onto parent and remove from this: ", str, index);
				movePrefixUp(str);				
				return true;	
			}
			
		} else if(this.string.equals(str)){
			// TO DO - Refactor line below to avoid checking class
			if(! this.needToSplitNode()  && ! (this.parent instanceof NodeRoot)){
				/* i.e the current node matches str 100%
				move the prefix to the parent and remove this node */				
				this.getParent().setSubstring(this.getParent().getSubstring() + this.getCommonPrefix(str));
				this.getParent().addChildren(this.children);
				this.getParent().removeChild(this);
				return true;
			} else {
//				debugTrace("Node string, = str", str, index);
				this.remove$Children();
				if(children.get(children.size()-1).getSubstring().equals("$")){
//					debugTrace("	Child has string of $, index changed only ", str, index);					
					children.get(children.size()-1).setSubstringIndex(index);				
					return true;
				} else {
//					debugTrace("	No child with $ value, creating leaf node ", str, index);			
					this.addChildLeaf("$", index);
					return true;
				}
			}
		} else if(this.nodeIsAPrefixOf(str)){/*if str is a prefix of this then iterate through child nodes */
//			debugTrace("Node is a prefix of:",  str, index);
			for(InnerNode child: children){
				if (child.addSubstring(this.removeNodeFromArg(str), index)){
					return true;
				}
			}
			return false;	
		} else if(this.string.equals("$")){ /*If this point reaches we are at last child because $ children always at the end*/			
//			debugTrace("Node = $ (" + this.stringIndex + ")", str, index);
			this.string = string;
			this.stringIndex = index;
			return true;
		} else {
//			debugTrace("Nothing matched in NodeNonLeaf " + this.string + "(" + this.stringIndex  + ") Returning false", str, index);
			return false;
		}
	}	
	/*
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
	
	/** 
	 * @inheritDoc
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
	
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public void printNode() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			element.printNode();
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
		this.children.add(child);
		
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
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public void setParent(Node parent) {
		this.parent = parent;
		
	}
	
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
	public Node getParent() {
		return this.parent;
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

	/*-----------------------------------------------------------------------------------------
	 * String getters and setters
	 *----------------------------------------------------------------------------------------*/
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public String getSubstring() {
		return this.string;
	}

	/** 
	 * @inheritDoc
	 */
	@Override
	public void setSubstringByIndex(int start) {
		this.string = this.string.substring(start);
	}

	/** 
	 * @inheritDoc
	 */
	@Override
	public int getSubstringIndex() {
		return this.stringIndex;
	}
	/** 
	 * @inheritDoc
	 */
	@Override
	public void setSubstring(String str) {
			this.string = str;
	}
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public void setSubstringIndex(int index) {
		this.stringIndex = index;
		
	}
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public String getfullString() {
		return this.parentPrefix + this.string;
	}

	/** 
	 * @inheritDoc
	 */
	@Override
	public List<Integer> getIndices() {
		return this.indices;
	}
	
}
