package substringAlgorithms;

/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
public class SuffixTree {
	//make this part of Node interface somehow?
	Node root;
	
	public SuffixTree(){
		root = new NodeTest("X",0); //TO DO FIX THIS
		// When a root is created it is created with one child, the terminal character $ and the index 0
		root.addChild(new NodeTest("$", 0));
	}
	
	public void addString(String str){		
		for(int i = 0; i < str.length(); i++){
			
			for(int j = 0; j <= i; j++){
				root.addSubString(str.substring(i, j), j);
			}
			
		}
		
		
	}

}

