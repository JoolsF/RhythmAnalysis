package suffixTreeAlternative;

public interface Node {

	
	
	public boolean addString(String str, int index); //returns true if string added successfully
	public void convertLeafToNode(NodeLeaf nodeToDelete, NodeNonLeaf replacementNode);
	public void addChildLeaf(String string, int index);
	
	
	
}
