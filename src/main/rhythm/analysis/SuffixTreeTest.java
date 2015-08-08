package rhythm.analysis;

import java.util.TreeMap;

import rhythm.analysis.model.suffixTree.SuffixTree;










public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree test = new SuffixTree(); 

		
		
		String str1 = "AB11AB11"; 
			
		test.addString(str1);
		
		
		System.out.println(test.getTree().nodesToList().toString());

		
		
		
	
	}

}
