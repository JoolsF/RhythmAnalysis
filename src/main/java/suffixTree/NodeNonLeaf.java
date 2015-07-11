package suffixTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeNonLeaf implements InnerNode {
	
	private String string;
	private int stringIndex;
	private Node parent;
	private List<InnerNode> children;
	
	
	public NodeNonLeaf(String string, int stringIndex, Node parent){
		
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
		this.children = new ArrayList<InnerNode>();
	}
	
	public NodeNonLeaf(String string, int StringIndex, Node parent, List<InnerNode> children){
		this(string, StringIndex, parent);
		this.children = children;
	}
	
	@Override
	public boolean addString(String str, int index) {
		
		
		if(this.nodeHasAPrefixOf(str)){
			debugTrace("Node has a a prefix of: ", str, index);
			//BASE CASE
			//TO DO check case if parent is root.
			if(this.needToSplitNode()){
				splitThisNode(str, index);
				return true;
			} else {
				System.out.println("  MOVE PREFIX ONTO PARENT AND REMOVE FROM THIS");
				//Move prefix onto parent and remove from this.
				movePrefixUp(str);				
				return true;	
			}
			
		} else if(this.string.equals(str)){
			//BASE CASE
			// TO DO TIDY THIS BLOCK UP
			//BASE CASES
			debugTrace("Node string, = str", str, index);
	//IF HAS A $ SIBLING THIS NEEDS TO BE REMOVED
	//IGNORE IF PARENT IS ROOT THOUGH
			this.remove$Children();
			if(children.get(children.size()-1).getString().equals("$")){
				debugTrace("	Child has string of $, index changed only ", str, index);					
				children.get(children.size()-1).setStringIndex(index);				
				return true;
			} else {
				debugTrace("	No child with $ value, creating leaf node ", str, index);			
				this.addChildLeaf("$", index);
				return true;
			}
		} else if(this.nodeIsAPrefixOf(str)){
			debugTrace("Node is a prefix of:",  str, index);
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
			//BUG HERE?
			debugTrace("Node = $ (" + this.stringIndex + ")", str, index);
			this.string = string;
			this.stringIndex = index;
			return true;
		} else {
			//ARE ALL CASES COVERED ABOVE
			debugTrace("Nothing matched in NodeNonLeaf " + this.string + "(" + this.stringIndex  + ") Returning false", str, index);
			return false;
		}
		
	}
	

		
	/**
	 * Private helper method for addString
	 */
	private void splitThisNode(String str, int index){
		//POTENTIAL BUG
				remove$Children();
		//POTENTIAL BUG
		String prefix = this.getCommonPrefix(str);  //A
		String suffix = this.removeArgFromNode(str);  //BA
		List<InnerNode> newNodeChildren = new ArrayList<InnerNode>();
		NodeNonLeaf newNode = new NodeNonLeaf(prefix, -1, this.parent, newNodeChildren); 
		this.parent = newNode;
		this.string = suffix;
		newNodeChildren.add(this); //adding this node to the new node's children
		newNodeChildren.add(new NodeLeaf("$",index, newNode)); //adding this node to the new node's children
		newNode.parent.removeChild(this);
		newNode.parent.addChild(newNode);
	}
	
	
	@Override
	public String getString() {
		return this.string;
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


	@Override
	public void setSubString(int start) {
		this.string = this.string.substring(start);
	}

	@Override
	public void printTree() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			element.printTree();
		}
		System.out.println("NODE: " + this.string + " (" + this.stringIndex + ") " 
							+ "\n" + "  Type: " + this.getClass().toString() 
							+ "\n  Children: " + this.getChildValues() +"\n");
	}

	@Override
	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}

	@Override
	public void setString(String str) {
			this.string = str;
	}

	@Override
	public void setStringIndex(int index) {
		this.stringIndex = index;
		
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
	public Node getParent() {
		return this.parent;
	}

	@Override
	public void addChild(InnerNode child) {
		this.children.add(child);
		
	}

	@Override
	public void removeChild(InnerNode child) {
		this.children.remove(child);
		
	}
	
	
}
