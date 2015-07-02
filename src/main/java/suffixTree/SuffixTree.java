package suffixTree;



/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */

public class SuffixTree {
	
	private Node root;
	
	public SuffixTree(String str){
		root = new NodeRoot();
		addString(str);
	}
	
	public void addString(String str){		
		for(int i = 0; i < str.length(); i++){			
			for(int index = 0; index <= i; index++){
				root.addString(str.substring(index, i+1), index);
				//System.out.println(str.substring(index, i+1));
			}	
			//$ added at the end of each substring iteration
			root.addString("$", i+1);
			//System.out.println("$ " + (i+1));
		}	
	}
	
	public Node getTree(){
		return this.root;
	}
	
	public static void main(String[] args){
		new SuffixTree("ababaabab");
	}
	
}
