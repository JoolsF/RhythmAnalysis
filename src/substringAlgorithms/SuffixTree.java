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
			for(int index = 0; index <= i; index++){
				root.addSubString(str.substring(index, i+1), index);
			}
			//$ added at the end of each substring iteration
			root.addSubString("$", i+1);
			
			
		}
		
		
	}
	
	public static void main(String [] args){
		new SuffixTree().addString("ab11");
		
	}

}

