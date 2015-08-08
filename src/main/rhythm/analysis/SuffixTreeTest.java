package rhythm.analysis;

import java.util.TreeMap;

import rhythm.analysis.model.SuffixTree_model;









public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree_model test = new SuffixTree_model(); 
		
<<<<<<< HEAD
		// WORKING UP TO AB11AA
		// WORKING 112233
		//
		// WORKING AB11001
		// WORKING AB110011AB
		// NOT WORKING WORKING 11001111
		// NOT WORKING 110011110 

		
		Rhythm_model test = new Rhythm_model(); //trace construction debug output carefully
		
		String str1 = "AB11AB11"; 
=======
		String str1 = "ABABABAB"; 
			
>>>>>>> branch 'master' of https://github.com/JoolsF/rhythm_analysis
		test.addString(str1);
		
<<<<<<< HEAD
		System.out.println(test.getTree().nodesToList().toString());
=======
//		test.getTree().processTree("");	
		test.getStringIndices();
>>>>>>> branch 'master' of https://github.com/JoolsF/rhythm_analysis
		
		
		
	
	}

}
