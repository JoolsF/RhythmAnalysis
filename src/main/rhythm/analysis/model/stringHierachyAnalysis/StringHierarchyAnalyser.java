package rhythm.analysis.model.stringHierachyAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import rhythm.analysis.control.RhythmController;
import rhythm.analysis.model.suffixTree.SuffixTree;



/**
 * 
 * Class responsible for analysing raw output of SuffixTree and returning data
 * for returning data suitable for rendering arc diagram in view.
 *
 */
//TO DO - Once prototype working will need improving as expensive computation involving lots of 
// reprocessing of maps
public class StringHierarchyAnalyser {
	
	//private SuffixTree suffixTree;
	private List<List<Integer>> stringPairData;
	
	
	public StringHierarchyAnalyser(){
		
	}
	
	public StringHierarchyAnalyser(SuffixTree suffixTree){
		this();
		this.stringPairData = new ArrayList<List<Integer>>();
	}
		
	
	
	
	public List<List<Integer>> getStringCoordinatesExactMatch(Map<String, List<Integer>> suffixMap, boolean applyStringAnalysis){
		return getStringCoordinates(suffixMap, applyStringAnalysis);
	}
	
	public List<List<Integer>> getStringCoordinatesInexactMatch(Map<String, List<Integer>> suffixMap, boolean applyStringAnalysis){
		//LevenshteinArc.getDifference(src, arc)
		//process suffix map through LevenshteinAnalyser to get similar matches
		return getStringCoordinates(LevenshteinAnalyser.findSimilarStrings(suffixMap),applyStringAnalysis);
	}
	
	
	/**
	 * Processes map "Substring -> Indices" and processes as follows
	 * 1. Remove overlapping substrings 
	 * 2. If substring is of length > 1 and has more that one index (i.e it repeats) then create
	 *    coordinates
	 */
	private List<List<Integer>>  getStringCoordinates(Map<String, List<Integer>> suffixMap,  boolean applyStringAnalysis){
		stringPairData = new ArrayList<List<Integer>>(); // for return
		List<StringPair> stringPairs = new ArrayList<StringPair>();
		for (Map.Entry<String, List<Integer>> entry :  getConsecutiveSubStrMap(suffixMap).entrySet()){
			String key = entry.getKey();
		 	int keyLength = key.length();
			List<Integer> value = entry.getValue();
			
			if(value.size() > 0){  // keep repeating substrings only		
				for(int i = 0; i < value.size() -1; i++){
					
					StringPair stringPair = new StringPair(value.get(i), value.get(i+1), keyLength, key);

					//TO DO pass in boolean test as function arg so that behaviour of method can be better modified and updated
					if(! applyStringAnalysis || stringValid(stringPairs, stringPair)){
						stringPairs.add(stringPair);
						//to do use arcPair below
						stringPairData.add(Arrays.asList(value.get(i), value.get(i) + (keyLength -1),
												  value.get(i+1), value.get(i+1) + (keyLength -1)));
					} 
				}
			}
		} 	
		return stringPairData;
	}
	
	private boolean stringValid(List<StringPair> stringPairlist,StringPair that){
		if(stringPairlist.isEmpty()) return true;
		
		for(StringPair next: stringPairlist){
			if(! next.stringValid(that)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * . Removes overlapping substrings e.g. take String ABABABAB
	 * 	 ABAB appears at 0,2,4.  However, ABAB at 2 intersects ABAB at 0
	 *   so it is discarded
	 *   
	 *   Also turns indices into pairs e.g input
	 *   ABAB=[0, 4]
	 *   ABAB=[0, 3, 4, 7] index to key length - 1
	 *   
	 *   Sorts by key length in descending order
	 */
	public TreeMap <String, List<Integer>> getConsecutiveSubStrMap(Map<String, List<Integer>> suffixMap){
		//Code changes default TreeMap ordering to key length in descending order
		
		TreeMap <String, List<Integer>> subStrMap = new TreeMap<String, List<Integer>>(getComparator());
		
		for (Map.Entry<String, List<Integer>> entry : suffixMap.entrySet()){	
			String key = entry.getKey();
			List<Integer> value = entry.getValue();
			//ensure the indices are sorted in ascending value
			Collections.sort(value);
			
			//substrings
				//i.e the first index in any list of integers is by definition valid
				//relies on List<Integer> being sorted in ascending order
				int lastValidIndex = value.get(0); 
				subStrMap.put(key, new ArrayList<Integer>());
				subStrMap.get(key).add(lastValidIndex);
				for(int i = 1; i < value.size(); i++){ // start at 1 as first entry will be correct
					if(entry.getValue().get(i) - lastValidIndex >= key.length()){
						lastValidIndex = value.get(i);
						subStrMap.get(key).add(lastValidIndex);
					} 
				}
		}	
		return subStrMap;
	}
	
	/**
	 * 
	 * Return a comparator for uses in Treemap sorted by key size in descending order
	 */
	//TO DO - Change comparator method to Java 8 style.
	// Code taken from Stack Overflow (INSERT LINK)
	private Comparator<String> getComparator(){
		Comparator<String> comp = new Comparator<String>(){
			@Override
			public int compare(String s1, String s2){
				if(s1.length() > s2.length()){
					return -1;
				} else if(s1.length() < s2.length()){
					return 1;
				} else {
					return s1.compareTo(s2);
				}	
			}
		};
		return comp;	
	}
				
	
}