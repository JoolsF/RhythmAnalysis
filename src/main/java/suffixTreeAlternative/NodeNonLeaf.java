package suffixTreeAlternative;

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
					this.string += strMinusPrefix;
					child.setSubString(strMinusPrefix.length());
					return true;
				} else if (child.nodeIsAPrefixOf(strMinusPrefix)){
					child.addString(strMinusPrefix, index);
				}
				
			}
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


	
	

		

}
