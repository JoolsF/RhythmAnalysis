package rhythm.analysis;

import java.util.TreeMap;

import rhythm.analysis.model.SuffixTree_model;









public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree_model test = new SuffixTree_model(); 
		
		String str1 = "ABABABAB"; 
			
		test.addString(str1);
		
//		test.getTree().processTree("");	
		test.getStringIndices();
		
		
		
	
	}

}
