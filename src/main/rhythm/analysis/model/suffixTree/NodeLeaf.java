package rhythm.analysis.model.suffixTree;

/**
 * Node leaf class.
 * Represents leaf nodes in the suffix tree
 * 
 * @author Julian Fenner
 */
import java.util.ArrayList;
import java.util.List;

public class NodeLeaf extends InnerNodeAbstract {

	private String string;
	private int stringIndex;
	private String parentPrefix;
	
	private Node parent;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
	public NodeLeaf(String string, int stringIndex, Node parent){	
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
		this.parentPrefix = "";
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Add string
	 *----------------------------------------------------------------------------------------*/
	/** 
	 * @inheritDoc
	 */
	@Override
	public boolean addSubstring(String string, int index) {
		if (this.nodeHasAPrefixOf(string)){
			/*BASE CASE - two sub cases*/ 
//			debugTrace("Node has a prefix, child has a prefix", string, index);
			if(this.needToSplitNode()){
//				debugTrace("Splitting Node ", string, index);	
				convertToNodeNonLeaf(string, index);
				return true;
			} else {
				this.movePrefixUp(string);
//				debugTrace("Moving prefix up ", string, index);
				return true;
			}
		} else if (this.nodeIsAPrefixOf(string)){
//			debugTrace("Node is a prefix ", string, index);
			/*BASE CASE*/			
			this.string += this.removeNodeFromArg(string);
			this.stringIndex = index;
			return true;
		}  else if(this.string.equals("$")){
//			debugTrace("This string = $", string, index);
			/*BASE CASE 
			I.e this is a leaf node without any value then you are at correct place and just need to update the field.
			Children will be sorted so that "$" for any given list of children will always be at the end. */
			this.string = string;
			this.stringIndex = index;
			return true;
		} else if (this.string.equals(string)){
//			debugTrace("this string = sting arg.  **DOING NOTHING CURRENTLY**", string, index);			
		} 
		/*if it gets to this point no string has been added and the method return false to confirm */
//		debugTrace("No matches and node returning false", string, index);
		return false;
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
		return getIndices();
	}
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public void printNode() {	
//		System.out.println("NODE: " + this.string + " (" + this.stringIndex + ")" 
//				+ "\n  Type: " + this.getClass().toString()+"\n");	
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Children methods
	 *----------------------------------------------------------------------------------------*/
	
	/** 
	 * @inheritDoc
	 */
	private void convertToNodeNonLeaf(String str, int index){		
		remove$Children();
		String prefix =  this.string.substring(0, str.length());
		String suffix = this.string.substring(str.length(), this.string.length());
		List<InnerNode> children = new ArrayList<InnerNode>();	
		
		NodeNonLeaf replacementNode = new NodeNonLeaf(prefix, -1, this.parent, children);		
		InnerNode leaf1 = new NodeLeaf(suffix, this.stringIndex, replacementNode); 
		InnerNode leaf2 = new NodeLeaf("$",index, replacementNode); /*Crucial that node containing "$" added second */ 
		children.add(leaf1);
		children.add(leaf2);		 		
		swapNode(this, replacementNode);	
	}
	
	/** 
	 * @inheritDoc
	 */
	@Override
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode) {
		parent.swapNode(nodeToDelete, replacementNode);	
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
	public Node getParent() {
		return this.parent;
	}


	
	//TO DO - Rethink interface, null implementation
	@Override
	public void addChild(InnerNode child) {
		// TODO Auto-generated method stub	
	}

	
	@Override
	//TO DO - Rethink interface, null implementation
	public void removeChild(InnerNode child) {
		// TODO Auto-generated method stub	
	}

	
	//TO DO - Rethink interface, null implementation
	@Override
	public void addChildren(List<InnerNode> children) {
		// TODO Auto-generated method stub	
	}
	
	
	//TO DO - Rethink interface, null implementation
	@Override
	public List<InnerNode> getChildren() {
		return new ArrayList<InnerNode>();
	}
	

	/*-----------------------------------------------------------------------------------------
	 * String getters and setters
	 *----------------------------------------------------------------------------------------*/
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
	public int getSubstringIndex() {
		return this.stringIndex;
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
	public String getSubstring() {
		return this.string;
	}

	/** 
	 * @inheritDoc
	 */
	@Override
	public String getfullString() {
		if(this.string == "$"){
			return this.string;
		} else {
			return this.parentPrefix + this.string;
		}
	}

	/** 
	 * @inheritDoc
	 */
	@Override
	public List<Integer> getIndices() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(this.stringIndex);
		return list;
	}

}
