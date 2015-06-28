package suffixTree;

/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
public class SuffixTree {
	//make this part of Node interface somehow?
	private Node root;
	
	public SuffixTree(){
		root = new NodeImpl("ROOT", -1); //TO DO FIX THIS
		// When a root is created it is created with one child, the terminal character $ and the index 0
		root.addChild(new NodeImpl("$", 0));
	}
	
	public void addString(String str){		
		for(int i = 0; i < str.length(); i++){			
			for(int index = 0; index <= i; index++){
				root.addSubString(str.substring(index, i+1), index);
				//System.out.println(str.substring(index, i+1));
			}	
			//$ added at the end of each substring iteration
			root.addSubString("$", i+1);
			//System.out.println("$ " + (i+1));
						
		}	
	}
	public Node getTree(){
		return this.root;
	}

}

