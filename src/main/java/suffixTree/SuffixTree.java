package suffixTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;



/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
/*
 * TO REFACTOR
 * CHANGE OVER CHILD NODES FOR HASHMAPS 
 * 	Means order of nodes won't matter i.e $ last and no need to iterate through children
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
				System.out.println("NODE TO ADD: " + str.substring(index, i+1) + "(" +index+")");
				root.addString(str.substring(index, i+1), index);
				
			}	
			//$ added at the end of each substring iteration
			root.addString("$", i+1);
			System.out.println("$ " + (i+1));
			System.out.println("%% END OF SUBSTRING %%");

		}	
	}
	
	public Node getTree(){
		return this.root;
	}
	
	public static void main(String[] args){
	//1100101 <- class cast error
	// AB1ABA WORKING
	// AB1ABAB WORKING
	// AB1ABAB1 WORKING
	// AB1ABAB1A WORKING
	// AB1ABAB1AB WORKING
	
		
		SuffixTree test = new SuffixTree("1010010"); 
		test.getTree().printTree();
		
		
		Map<String, List<Integer>> nodeMap = test.getTree().nodesToMap(new TreeMap<String, List<Integer>>());
		for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
    		System.out.println(value.getKey() + ": " + value.getValue());
    	}

		
	}
	
}
