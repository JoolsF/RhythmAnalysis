package rhythm.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.arcAnalysis.ArcAnalyser;
import rhythm.analysis.model.arcAnalysis.ArcPair;
import rhythm.analysis.model.arcAnalysis.LevenshteinArc;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.Arc_viewer;



public class SuffixTreeTest {

	
	
	
	public static void main(String[] args){	
//		SuffixTree testSuffixTree = new SuffixTree(); 
		

		String str1 = "123a223b123c";
		String str2 = "123a123b";
		String str3 = "12345671234568";
		Rhythm_controller testController = new Rhythm_controller();
		
		testController.updateTree(str1);
//		Map<String, List<Integer>> same = testController.getTreeAsMap();
//		Map<String, List<Integer>> similar = LevenshteinArc.findSimilarStrings(testController.getTreeAsMap()); 
//		
//		ArcAnalyser arcAnalyser = new ArcAnalyser();
//		System.out.println("Same " + arcAnalyser.getArcCoordinatesExactMatch(same));
//		System.out.println("Similar " + arcAnalyser.getArcCoordinatesExactMatch(similar));
		
	}
}