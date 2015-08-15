package rhythm.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.arcAnalysis.ArcAnalyser;
import rhythm.analysis.model.arcAnalysis.ArcPair;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.Arc_viewer;










public class SuffixTreeTest {

	public static void main(String[] args){	
		SuffixTree testSuffixTree = new SuffixTree(); 
		Rhythm_controller testController = new Rhythm_controller();
		ArcAnalyser x = new ArcAnalyser();
		
		String str1 = "123a123b123c";
		String str2 = "11111111";
		String str3 = "12qwqw12";
		
		String str4 = "AB1212AB";
		String str5 = "12ABAB12";
		String str6 = "ABCDABCDCD";
		
		testController.updateTree(str1);
		for(List<Integer> next: testController.getMatchingStrings()){
			System.out.println(next);
		}
		
		System.out.println(testController.getTreeAsList());
//
//		ArcPair parent1 = new ArcPair(0,5,4);
//		ArcPair parent2 = new ArcPair(1,6,3);
//		System.out.println(parent1.arcValid(parent2));
		
	
	}

}
