package rhythm.analysis.model;



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
		System.out.println("ROOT:  \n  Children: " + this.getChildValues()+"\n");
		while(itr.hasNext()){
			InnerNode element = itr.next();
			element.printTree();
		}
		
	}

	@Override
	public List<InnerNode> getChildren() {
		return this.children;
	}
	
	
	//TO DO - refactor this is in InnerNode interface too
	private String getChildValues(){
		String childValues = "";
		//guard condition needed for LeafNode
		if(this.getChildren() != null) {
			for(InnerNode next: this.getChildren()){
				childValues += next.getString() + "(" +next.getStringIndex()+")  - ";
				}
		}
		return childValues;	
	}

	@Override
	public void setString(String str) {
		//HAS NO STRING NEED TO RETHINK THE INTERFACE DESIGN
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(InnerNode child) {
		if(child.getString().equals("$")){
			this.children.add(this.children.size(),child);	
		} else {
			
			this.children.add(0,child);
		}	
		
	}

	@Override
	public void removeChild(InnerNode child) {
		this.children.remove(child);
		
	}

	@Override
	public void addChildren(List<InnerNode> children) {
		for(InnerNode next: children){
			next.setParent(this);
			
			if(next.getString().equals("$")){
				this.children.add(this.children.size(),next);	
			} else {
				this.children.add(0,next);
			}	
			
		}
		
	}
}
