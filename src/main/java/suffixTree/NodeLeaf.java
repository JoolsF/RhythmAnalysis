package suffixTree;


import java.util.ArrayList;
import java.util.List;


public class NodeLeaf implements InnerNode {

	private String string;
	private int stringIndex;
	private Node parent;
	
	public NodeLeaf(String string, int stringIndex, Node parent){
		
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
	}

	@Override
	public boolean addString(String string, int index) {
		if (this.nodeHasAPrefixOf(string)){
			//BASE CASE
			//HAS A PREFIX CASES ARE SPLIT / NEW NODE CASES
			//convert leaf to node
			// NEEDS TO GO BACK TO PARENT, GET DELETED AND HAVE A NONLEAF NODE PUT IN ITS PLACE
			debugTrace("Node has a prefix, child has a prefix", string, index);
			if(this.needToSplitNode()){
				prepNodeSwap(string, index);
				return true;
			} else {
				this.movePrefixUp(string);
				return true;
			}
			
		
		} else if (this.nodeIsAPrefixOf(string)){
			debugTrace("Node is a prefix ", string, index);
			//BASE CASE			
			this.string += this.removeNodeFromArg(string);
			this.stringIndex = index;
			return true;
		}  else if(this.string.equals("$")){
			debugTrace("This string = $", string, index);
			//BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			this.string = string;
			this.stringIndex = index;
			return true;
		} else if (this.string.equals(string)){
			//SEE NODENONLEAF
			debugTrace("this string = sting arg.  **DOING NOTHING CURRENTLY**", string, index);			
			// DOES THIS CASE OCCUR??
		} 
		//IF IT GETS TO THIS POINT NO STRING HAS BEEN ADDED AND THE METHOD RETURNS FALSE TO CONFIRM
		debugTrace("No matches and node returning false " + this.string + "(" + this.stringIndex +")", string, index);
		return false;
	}

	@Override
	public String getString() {
		return this.string;
	}
	
	//TO DO - reassess having public method here not in Node interface
	public void prepNodeSwap(String str, int index){
		
//POTENTIAL BUG
		remove$Children();
//POTENTIAL BUG
		String prefix =  this.string.substring(0, str.length());
		String suffix = this.string.substring(str.length(), this.string.length());
		List<InnerNode> children = new ArrayList<InnerNode>();	
		
		NodeNonLeaf replacementNode = new NodeNonLeaf(prefix, -1, this.parent, children);		
		InnerNode leaf1 = new NodeLeaf(suffix, this.stringIndex, replacementNode); 
		InnerNode leaf2 = new NodeLeaf("$",index, replacementNode); 				
		//important that node containing "$" added second
		children.add(leaf1);
		children.add(leaf2);		
		 		
		swapNode(this, replacementNode);	
	}
	

	@Override
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode) {
		parent.swapNode(nodeToDelete, replacementNode);	
	}

	

	@Override
	public void setSubString(int start) {

		this.string = this.string.substring(start);
	}

	@Override
	public void printTree() {
		
//		System.out.println("NODE: " + this.string + " (" + this.stringIndex + ")" 
//				+ "\n  Type: " + this.getClass().toString()+"\n");
		
	}

	@Override
	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public List<InnerNode> getChildren() {
		return new ArrayList<InnerNode>();
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
	public void setParent(Node parent) {
		this.parent = parent;
		
	}

	@Override
	public Node getParent() {
		return this.parent;
	}

	//THESE TWO METHODS SHOULDN'T BE HERE
	@Override
	public void addChild(InnerNode child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(InnerNode child) {
		// TODO Auto-generated method stub
		
	}

	

	


}
