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
		System.out.println("HERE FIRST");
		if(this.nodeIsAPrefixOf(str)){
			//i.e this.string = a and str arg is ab then a is a prefix of ab
			//now need to check children for b as we have matched the a prefix
			// if one of the children's string HAS a prefix of b i.e bab then we need to take this prefix
			// from the child i.e ab and "move it onto the a above
			// str = ab
			//	this = a
			// child = bab
			// strMinusPrefix = b
			// commonPrefix = a

			//child bab IS A prefix of strminusprefix bab 
			String strMinusPrefix = this.removeNodeFromArg(str);
			String commonPrefix = this.getCommonPrefix(str);
			for(InnerNode child: children) {
				if(child.nodeHasAPrefixOf(strMinusPrefix)){
					//BASE CASE
					this.string += strMinusPrefix;
					child.setSubString(strMinusPrefix.length());
					System.out.println("child string " + child.getString());
					System.out.println("str minus prefix " + strMinusPrefix);
					
					return true;
				} else if (child.nodeIsAPrefixOf(strMinusPrefix)){
System.out.println("IN HERE");
					//i.e recursive case has next node is also a prefix of the string minus the prefix
					System.out.println("STRING MINUS PREFIX: " + strMinusPrefix);
					child.addString(strMinusPrefix, index);
					return true;
				} // FINAL CASE TO ADD??
				
			}
		} else if(this.nodeHasAPrefixOf(str)){
			//BASE CASE (case in "3 Bug Potential Fix")
			/* this means that we are in non leaf node i.e ABA(-1) and the incoming str is A(4)
			 *                                            
			 * 			node----R----node
			 * 					|
			 * 					|
			 * 				   ABA(-1)         <---- A(4)
			 * 				  /   \
			 *               /     \
			 *             $(2)	    BA(0)
			 *
			 *             
			 *         node----R----node
			 * 				   |
			 * 				   |
			 * 				   A
			 * 				  /	\
			 * 			     /   \
			 * 		        /	  \		
			 * 			$(4)	  BA(-1)                   
			 * 				      /   \
			 *                   /     \
			 *                 $(2)	    BA(0)
			 * 
			 *             
			 *             
			 * we must split the node ABA into A |BA
			 * 1 new node is created A(-1) with 2 children
			 * 		$(4) (terminal symbol + strindex)
			 * 		The tree with BA(-1) as the head
			 * 		
			 * 2. new node's parent is BA(-1)s parent (R)
			 * 3.  BA(-1s) parent is new node A(-1)
			 * 		parent (R) has BA(-1) removed from its list of children
			 * 		new child A(-1) added to Parent(R) children
			 * 
			 
			 *             
			 *             
			 * */
			splitThisNode(str, index);
			return true;
			
		}
		//if nothing matched
		return false;
	}
	/**
	 * Private helper method for addString
	 */
	private void splitThisNode(String str, int index){
		String prefix = this.getCommonPrefix(str);  //A
		String suffix = this.removeArgFromNode(str);  //BA
		this.string = suffix;
		
		List<InnerNode> newNodeChildren = new ArrayList<InnerNode>();
		
		NodeNonLeaf newNode = null;
		newNodeChildren.add(this); //adding this node to the new node's children
		newNodeChildren.add(new NodeLeaf("$",index, newNode)); //adding this node to the new node's children
		newNode = new NodeNonLeaf(prefix, -1, this.parent, newNodeChildren); 
		this.parent.swapNode(this, newNode);	
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
		System.out.println(" NODE: " + this.string + " (" + this.stringIndex + ") " + "type: " + this.getClass().toString());  //-> children: " + element.getChildStrings());
	}

	@Override
	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}

	

}
