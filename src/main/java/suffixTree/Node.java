package suffixTree;

public interface Node {

	public boolean addString(String str, int index); //returns true if string added successfully
	public void swapNode(InnerNode nodeToDelete, InnerNode replacementNode);
	public void printTree();
		
}
