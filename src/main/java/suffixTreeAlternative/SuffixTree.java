package suffixTreeAlternative;

public class SuffixTree {
	
	Node root;
	
	public SuffixTree(String str, int index){
		root = new NodeRoot();
		root.addString(str, index);
	}

}
