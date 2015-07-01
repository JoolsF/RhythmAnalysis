package suffixTreePrototype2;

import java.util.ArrayList;
import java.util.List;

public class NodeNonLeaf implements InnerNode {
	
	private String string;
	private int stringIndex;
	private Node parent;
	private List<InnerNode> children;
	
	
	public NodeNonLeaf(String string, int StringIndex, Node parent){
		
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
					return true;
				} else if (child.nodeIsAPrefixOf(strMinusPrefix)){
					//i.e recursive case has next node is also a prefix of the string minus the prefix
					child.addString(strMinusPrefix, index);
				} // FINAL CASE TO ADD??
				
			}
		} else if(this.nodeHasAPrefixOf(str)){
			//BASE CASE (case in "3 Bug Potential Fix")
			/* this means that we are in non leaf node i.e ABA(-1) and the incoming str is A(4)
			 *                                            
			 * 			node----R----node
			 * 					|
			 * 					|
			 * 				   ABA(-1)                    <---- A(4)
			 * 				  /   \
			 *               /     \
			 *             $(2)	    BA(0)
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
			 * */
			
			String prefix = this.getCommonPrefix(str);
			String suffix = this.removeArgFromNode(str);
			
			
			List<InnerNode> newNodeChildren = new ArrayList<InnerNode>();
			newNodeChildren.add(this);
			Node newNode = new NodeNonLeaf(prefix, -1, this.parent, newNodeChildren);
			this.parent.removeChild(this);
			
		}
		//if nothing matched
		return false;
		
	}

	@Override
	public String getString() {
		return this.string;
	}

	


	@Override
	public void convertLeafToNode(NodeLeaf nodeToDelete, NodeNonLeaf replacementNode) {
		this.children.remove(nodeToDelete);
		this.children.add(replacementNode);
		
	}

	@Override
	public void addChildLeaf(String string, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSubString(int start) {
		this.string = this.string.substring(start);
	}

	@Override
	public void removeChild(Node child) {
		// TODO Auto-generated method stub
		
	}


	
	

		

}
