package suffixTreePrototype2;

import java.util.ArrayList;
import java.util.List;


//should be singleton
//shouldn't have getParent and addchild, rethink
public class NodeRoot implements Node {

	
	private List<Node> children;
		
		public NodeRoot(){
			this.children = new ArrayList<Node>();
		}
		
	/*CASES
	* 1 no children create new leaf node
	* 2 children
	*	a  str is not a "prefix / doesnt match" any children
	*		last child has $ value then str value give to to this child
	*	b still no matches then create new leaf node (only going to happen to $ after first iteration
	*/		
	@Override
	public boolean addString(String string, int index) {
		if (this.children.isEmpty()){
			this.children.add(new NodeLeaf(string, index, this));
			return true;
		} else{
			for(Node child: children){
				//for loop correct here?
				if(child.addString(string, index)){
					return true;
				}
			}
		}
		//i.e no matches found
		addChildLeaf(string, index);
		return true;
		
	}

	@Override
	public void convertLeafToNode(NodeLeaf nodeToDelete, NodeNonLeaf replacementNode) {
		this.children.remove(nodeToDelete);
		this.children.add(replacementNode);	
	}

	@Override
	public void addChildLeaf(String string, int index) {
		Node child = new NodeLeaf(string, index, this);
		if(string.equals("$")){
			this.children.add(this.children.size(),child);	
		} else {
			
			this.children.add(0,child);
		}
		
	}

	@Override
	public void removeChild(Node child) {
		// TODO Auto-generated method stub
		
	}

	

	



}
