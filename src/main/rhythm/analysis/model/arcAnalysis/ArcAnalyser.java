package rhythm.analysis.model.arcAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import rhythm.analysis.model.suffixTree.SuffixTree;



/**
 * 
 * Class responsible for analysing raw output of SuffixTree and returning data
 * for returning data suitable for rendering arc diagram in view.
 *
 */
//TO DO - Once prototype working will need improving as expensive computation involving lots of 
// reprocessing of maps
public class ArcAnalyser {
	
	private SuffixTree suffixTree;
	
	public ArcAnalyser(SuffixTree suffixTree){
		this.suffixTree = suffixTree;
		
	}
	/**
	 * Processes map "Substring -> Indices" and processes as follows
	 * 1. Remove overlapping substrings 
	 * 2. If substring is of length > 1 and has more that one index (i.e it repeats) then create
	 *    coordinates
	 */
	public List<List<Integer>>  getArcCoordinates(){
		List<List<Integer>> arcData = new ArrayList<List<Integer>>();  
		TreeMap<String, List<Integer>> x = getConsecutiveSubStrMap();
		
		
		
//		for(Entry<String, List<Integer>> next: getConsecutiveSubStrMap().entrySet()){
//			System.out.println(next.getKey());
//			System.out.println(next.getValue());
//			System.out.println("--------");
//			
//		}
//		
//		for (Map.Entry<String, List<Integer>> entry : arcFilter().entrySet()){	
		 for (Map.Entry<String, List<Integer>> entry :  getConsecutiveSubStrMap().entrySet()){
			String key = entry.getKey();
			List<Integer> value = entry.getValue();

			for(int i = 0; i < value.size() -1; i++){
				arcData.add(Arrays.asList(value.get(i),
										  value.get(i) + (key.length() -1),
										  value.get(i+1),
										  value.get(i+1) + (key.length() -1)));	
			}		
		}
		return arcData;
	}
	
	/**
	 * 
	 * Gets a node map and processes through helper method getConsecutiveSubStrMap which removes overlaps
	 * 
	 */
	public Map <String, List<Integer>> arcFilter(){
		//Removes any values < 2
		Map <String, List<Integer>> arcMap = new TreeMap <String, List<Integer>>();
		for (Map.Entry<String, List<Integer>> entry : getConsecutiveSubStrMap().entrySet()){
			
			
		}
		
		return arcMap;
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
	private TreeMap <String, List<Integer>> getConsecutiveSubStrMap(){
		//Code changes default TreeMap ordering to key length in descending order
		//TO DO - Change comparator method to Java 8 style.
		// Code taken from Stack Overflow (INSERT LINK)
		TreeMap <String, List<Integer>> subStrMap = new TreeMap <String	, List<Integer>>(
				new Comparator<String>(){
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
				});
		
		for (Map.Entry<String, List<Integer>> entry : this.suffixTree.getSubStringMap().entrySet()){	
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
	
	
}