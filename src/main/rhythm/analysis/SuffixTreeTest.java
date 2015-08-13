package rhythm.analysis;

import java.util.TreeMap;

import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.suffixTree.SuffixTree;










public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree testSuffixTree = new SuffixTree(); 
		Rhythm_controller testController = new Rhythm_controller();
		

		
		
		String str1 = "ABABABAB"; 
		//testSuffixTree.addString(str1);
		testController.updateTree(str1);
		//System.out.println(test.getTree().nodesToList().toString());

		
		
		
	
	}

}
