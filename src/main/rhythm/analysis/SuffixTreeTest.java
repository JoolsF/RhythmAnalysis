package rhythm.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import rhythm.analysis.control.RhythmController;
import rhythm.analysis.model.stringHierachyAnalysis.LevenshteinAnalyser;
import rhythm.analysis.model.stringHierachyAnalysis.StringHierarchyAnalyser;
import rhythm.analysis.model.stringHierachyAnalysis.StringPair;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.ArcViewer;



public class SuffixTreeTest {

	
	
	
	public static void main(String[] args){	
		SuffixTree testSuffixTree = new SuffixTree(); 
		StringHierarchyAnalyser sha = new StringHierarchyAnalyser();

		String str1 = "abababababab";
		String str2 = "123a123b";
		String str3 = "12345671234568";
		testSuffixTree.addString(str1);
		System.out.println(testSuffixTree.getTree().nodesToMap(new TreeMap<String, List<Integer>>()));
		
		//System.out.println(sha.getStringCoordinatesExactMatch(testSuffixTree.getTree().nodesToMap(new TreeMap<String, List<Integer>>())));
		
//		Rhythm_controller testController = new Rhythm_controller();
//		
//		testController.updateTree(str1);
//		System.out.println(testController.getTreeAsMap());
//		Map<String, List<Integer>> same = testController.getTreeAsMap();
//		Map<String, List<Integer>> similar = LevenshteinArc.findSimilarStrings(testController.getTreeAsMap()); 
//		
//		ArcAnalyser arcAnalyser = new ArcAnalyser();
//		System.out.println("Same " + arcAnalyser.getArcCoordinatesExactMatch(same));
//		System.out.println("Similar " + arcAnalyser.getArcCoordinatesExactMatch(similar));
		
	}
}