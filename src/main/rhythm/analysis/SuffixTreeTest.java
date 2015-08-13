package rhythm.analysis;

import java.util.TreeMap;

import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.arcAnalysis.ArcAnalyser;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.Arc_viewer;










public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree testSuffixTree = new SuffixTree(); 
		Rhythm_controller testController = new Rhythm_controller();
		ArcAnalyser x = new ArcAnalyser();
		
		
		
		
		String str1 = "ABABABAB"; 
		//testSuffixTree.addString(str1);
		testController.updateTree(str1);
		System.out.println(testController.getMatchingStrings());

		
		
		
	
	}

}
