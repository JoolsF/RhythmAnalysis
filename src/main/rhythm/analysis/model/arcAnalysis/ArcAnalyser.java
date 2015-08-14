package rhythm.analysis.model.arcAnalysis;

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
//	public Map <Integer, List<List<Integer>>> usedIndices;
	
	public ArcAnalyser(){
//		this.usedIndices = new TreeMap<Integer, List<List<Integer>>>();
		
	}
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
		List<List<Integer>> arcData = new ArrayList<List<Integer>>(); // for return
		//List<List<Integer>> indicesUsedSoFar = new ArrayList<List<Integer>>();
		
		
		
		
			
		 for (Map.Entry<String, List<Integer>> entry :  getConsecutiveSubStrMap().entrySet()){
		 	String key = entry.getKey();
		 	int keyLength = key.length();
			List<Integer> value = entry.getValue();
			
			if(value.size() > 0){ 
				
				for(int i = 0; i < value.size() -1; i++){
					int thisValStart = value.get(i);
					int thisValEnd = thisValStart + keyLength - 1;
					int nextValStart = value.get(i + 1);
					int nextValEnd = nextValStart + keyLength - 1;
					
					List<Integer> thisVal = getSequenceAsList(thisValStart, thisValEnd);
					List<Integer> nextVal = getSequenceAsList(nextValStart, nextValEnd);
					List<Integer> bothVal = new ArrayList<Integer>(thisVal);
					bothVal.addAll(nextVal);
					//NEED TO CHECK IF PAIRS ARE VALID
//					if(validSubString(indicesUsedSoFar, thisVal)) { 				
						
						
						arcData.add(Arrays.asList(value.get(i),
												  value.get(i) + (keyLength -1),
												  value.get(i+1),
												  value.get(i+1) + (keyLength -1)));
	//					indicesUsedSoFar.add(new ArrayList<Integer>(thisVal));
		//				indicesUsedSoFar.add(new ArrayList<Integer>(nextVal));
			//		} //end if
				}	
			}
		} //end for		
	
		return arcData;
	}
	


	
	
	private List<Integer> getSequenceAsList(int from, int to){
		List<Integer> sequenceList = new ArrayList<Integer>();
		
		for(int i = from; i <= to; i ++) sequenceList.add(i);
		
		return sequenceList;
	}
	public boolean validSubString(List<List<Integer>> listOfLists, List<Integer> list){
		
		for(List<Integer> nextList: listOfLists){
				if(list.size() < nextList.size()){
				System.out.println("----------------");
				System.out.println(list);
				System.out.println(nextList);
				
				boolean contains = false;
				boolean doesNotContain = false;
				
				for(Integer nextInt: list){
					if(nextList.contains(nextInt)){
						contains = true;
					} else {
						doesNotContain = true;
					}
				}
				
				if((contains && doesNotContain) == true){
					return false;
				}
			}//end if
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
	private TreeMap <String, List<Integer>> getConsecutiveSubStrMap(){
		//Code changes default TreeMap ordering to key length in descending order
		
		TreeMap <String, List<Integer>> subStrMap = new TreeMap<String, List<Integer>>(getComparator());
		
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
	
	/**
	 * 
	 * Return a comparator for uses in Treemap sorted by key size in descending order
	 */
	//TO DO - Change comparator method to Java 8 style.
	// Code taken from Stack Overflow (INSERT LINK)
	public Comparator<String> getComparator(){
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