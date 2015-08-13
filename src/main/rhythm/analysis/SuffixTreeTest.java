package rhythm.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		testController.updateTree(str1);
		testController.getMatchingStrings();

		
		
		
//		List<Integer> set1 = new ArrayList<Integer>();
//		
//		List<Integer> set2 = new ArrayList<Integer>();
//		set2.add(4);
//		set2.add(5);
//		set2.add(6);
//		
//		List<List<Integer>> listSet = new ArrayList<List<Integer>>();
//		
//		listSet.add(set1);
//		listSet.add(set2);
//		
//		
//		List<Integer> subset = new ArrayList<Integer>();
//		subset.add(2);
//		subset.add(3);
//		
//		
//		
//		
//		System.out.println(x.validSubString(listSet, subset));
		
		
	
	}

}
