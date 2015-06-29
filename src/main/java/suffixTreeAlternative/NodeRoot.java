package suffixTreeAlternative;

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
	public void addString(String string, int index) {
		if (this.children.isEmpty()){
			children.add(new NodeLeaf(string, index, this));
		} else{
			for(Node child: children){
				//for loop correct here?
				child.addString(string, index);
				//add return here?
			}
		}
		
	}

	



}
