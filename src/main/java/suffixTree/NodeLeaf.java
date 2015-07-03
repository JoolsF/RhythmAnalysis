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
		if(this.string.equals("$")){
debugTrace("This string = $", string, index);
			//BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			//TO DO - UPDATE INDEX TOO FOR SAFETY?
//			System.out.println("String " + string + ": (" + index +")");
//			System.out.println(this.removeArgFromNode(string);
			this.string = string;
			this.stringIndex = index;
			return true;
		} else if (this.string.equals(string)){
			// DO SOMETHING
debugTrace("this string = sting arg.  **DOING NOTHING CURRENTLY**", string, index);			
			// DOES THIS CASE OCCUR??
		} else if (this.nodeIsAPrefixOf(string)){
debugTrace("Node is a prefix ", string, index);
			//BASE CASE			
			this.string += this.removeNodeFromArg(string);
			this.stringIndex = index;
			return true;
		} else if (this.nodeHasAPrefixOf(string)){
debugTrace("Node has a prefix, child has a prefix", string, index);
			//BASE CASE
			//HAS A PREFIX CASES ARE SPLIT / NEW NODE CASES
			//convert leaf to node
			// NEEDS TO GO BACK TO PARENT, GET DELETED AND HAVE A NONLEAF NODE PUT IN ITS PLACE
			
			prepNodeSwap(string, index);
			return true;
			
		}
		//IF IT GETS TO THIS POINT NO STRING HAS BEEN ADDED AND THE METHOD RETURNS FALSE TO CONFIRM
debugTrace("No matches and node returning false", string, index);
		return false;
	}

	@Override
	public String getString() {
		return this.string;
	}
	
	//TO DO - reassess having public method here not in Node interface
	public void prepNodeSwap(String str, int index){
		String prefix =  this.string.substring(0, str.length());
		String suffix = this.string.substring(str.length(), this.string.length());
//		System.out.println("String: " + str);
//		System.out.println("String index: " + index);
//		System.out.println("prefix: "+ prefix);
//		System.out.println("suffix: "+ suffix);
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
		System.out.println(" NODE: " + this.string + " (" + this.stringIndex + ") type: " + this.getClass().toString());
		
	}

	@Override
	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public List<InnerNode> getChildren() {
		return new ArrayList<InnerNode>();
	}

	

	


}
