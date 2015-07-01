package suffixTreePrototype2;

import java.util.ArrayList;
import java.util.List;

import suffixTreeProtoType1.NodeImpl;


public class NodeLeaf implements InnerNode {

	String string;
	int stringIndex;
	Node parent;
	
	public NodeLeaf(String string, int StringIndex, Node parent){
		
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
	}

	@Override
	public boolean addString(String string, int index) {
		if(this.string.equals("$")){
			//BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			//TO DO - UPDATE INDEX TOO FOR SAFETY?
			this.string = this.removeNodeFromArg(string);
			this.stringIndex = index;
			return true;
		} else if (this.string.equals(string)){
			// DO SOMETHING
			// DOES THIS CASE OCCUR??
		} else if (this.nodeIsAPrefixOf(string)){
			//BASE CASE
//BUG need to add suffix of string (by minusing node from string)			
			this.string += string;
			this.stringIndex = index;
			return true;
		} else if (this.nodeHasAPrefixOf(string)){
			//BASE CASE
			//HAS A PREFIX CASES ARE SPLIT / NEW NODE CASES
			//convert leaf to node
			// NEEDS TO GO BACK TO PARENT, GET DELETED AND HAVE A NONLEAF NODE PUT IN ITS PLACE
			String prefix =  this.string.substring(0, string.length());
			String suffix = this.string.substring(string.length(), this.string.length());
			
			//TO DO move into a method so can be fully tested.
			//Build replacement node
			NodeNonLeaf replacementNode = null;		
			InnerNode leaf1 = new NodeLeaf(suffix, this.stringIndex, replacementNode); 
			InnerNode leaf2 = new NodeLeaf("$",index, replacementNode); 			
			List<InnerNode> children = new ArrayList<InnerNode>();		
			//important that node containing "$" added second
			children.add(leaf1);
			children.add(leaf2);		
			replacementNode = new NodeNonLeaf(prefix, -1, this.parent, children);			
			this.convertLeafToNode(this, replacementNode);
			//TO DO CHECK RETURN TRUE MAKES SENSE HERE
			return true;
			
		}
		//IF IT GETS TO THIS POINT NO STRING HAS BEEN ADDED AND THE METHOD RETURNS FALSE TO CONFIRM
		return false;
	}

	@Override
	public String getString() {
		return this.string;
	}

	@Override
	public void convertLeafToNode(NodeLeaf nodeToDelete, NodeNonLeaf replacementNode) {
		parent.convertLeafToNode(nodeToDelete, replacementNode);
		
	}

	@Override
	public void addChildLeaf(String string, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSubString(int start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(Node child) {
		// TODO Auto-generated method stub
		
	}




}
