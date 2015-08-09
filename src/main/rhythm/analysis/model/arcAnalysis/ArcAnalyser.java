package rhythm.analysis.model.arcAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
	public List<List<Integer>>  getArcCoordinates(int minSize, int minLength){
		List<List<Integer>> arcData = new ArrayList<List<Integer>>();  
		
		//for (Map.Entry<String, List<Integer>> entry : arcFilter(1,1).entrySet()){	
		 for (Map.Entry<String, List<Integer>> entry :  getConsecutiveSubStrMap().entrySet()){
			String key = entry.getKey();
			List<Integer> value = entry.getValue();

			if(entry.getValue().size() > minSize && entry.getKey().length() > minLength) {
				//Guard condition to prevent repeating single characters and non-repeating
				//substrings
				for(int i = 0; i < value.size() -1; i++){
					arcData.add(Arrays.asList(value.get(i),
											  value.get(i) + (key.length() -1),
											  value.get(i+1),
											  value.get(i+1) + (key.length() -1)));	
				}
			}		
		}
		return arcData;
	}
	
	/**
	 * 
	 * Gets a node map and processes through helper method getConsecutiveSubStrMap which removes overlaps
	 * It then removes arcs below minimum size and minimum length and applies arc rules
	 */
	public Map <String, List<Integer>> arcFilter(int minSize, int minLength){
		Map <String, List<Integer>> arcMap = new TreeMap <String, List<Integer>>();
		for (Map.Entry<String, List<Integer>> entry : getConsecutiveSubStrMap().entrySet()){
			
			
		}
		
		return arcMap;
	}
	
	
	/**
	 * . Removes overlapping substrings e.g. take String ABABABAB
	 * 	 ABAB appears at 0,2,4.  However, ABAB at 2 intersects ABAB at 0
	 *   so it is discarded
	 */
	private Map <String, List<Integer>> getConsecutiveSubStrMap(){
		Map <String, List<Integer>> subStrMap = new TreeMap <String, List<Integer>>();
		for (Map.Entry<String, List<Integer>> entry : this.suffixTree.getSubStringMap().entrySet()){	
			String key = entry.getKey();
			List<Integer> value = entry.getValue();
			//ensure the indices are sorted in ascending value
			Collections.sort(value);
			
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
		for (Map.Entry<String, List<Integer>> entry : subStrMap.entrySet()){
			
			System.out.println(entry);
		}
		
		return subStrMap;
		
	}
	
	
	
		
	}
