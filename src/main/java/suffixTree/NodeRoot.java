package suffixTree;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



//should be singleton
//shouldn't have getParent and addchild, rethink
public class NodeRoot implements Node {

	//to do change to private
	public List<InnerNode> children;
		
		public NodeRoot(){
			this.children = new ArrayList<InnerNode>();
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
System.out.println("	Children empty.  Creating node " + string +"("+index+")");			
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
System.out.println("	No matches round at root adding: " + string +"("+index+")");		
		addChildLeaf(string, index);
		return true;
		
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
	

	private void addChildLeaf(String string, int index) {
		InnerNode child = new NodeLeaf(string, index, this);
		if(string.equals("$")){
			this.children.add(this.children.size(),child);	
		} else {
			
			this.children.add(0,child);
		}		
	}

	@Override
	public void printTree() {
		Iterator<InnerNode> itr = children.iterator();
		while(itr.hasNext()){
			Node element = itr.next();
			//System.out.println(" NODE: " + element.getSubString() + " (" + element.getSubStringIndex() + ") -> children: " + element.getChildStrings());
			element.printTree();
		}
		
	}

	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}
	

	
	
	
}
