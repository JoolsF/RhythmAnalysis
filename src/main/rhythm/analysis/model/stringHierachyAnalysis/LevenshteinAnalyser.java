package rhythm.analysis.model.stringHierachyAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * Utility class for Arc Analysis containing one public static method getDifference. 
 * Note - This implementation is a HIGHLY inefficient, 'naive' implementation, and serves only as a proof of concept.
 * Intended for strings of less than one thousand characters in length
 *
 */
public class LevenshteinAnalyser {
	
	/**
	 * Get difference gets the Levenshtein between two strings divided by the length of the first string. 
	 * Intended for use on strings of equal length.
	 * @param the first string to be compared
	 * @param the second string to be compared
	 * @return the Levenshtein distance between the two string parameters divided by the length of the first string
	 */
	public static double getDifference(String string1, String string2){
		double difference = StringUtils.getLevenshteinDistance(string1, string2);
		return (difference / (string1.length()));
	}
	
	private static List<String> valueToString(String value){
		List<String> list = new ArrayList<String>();
		char[] charArray = value.toCharArray();
		for(int i = charArray.length -1; i > 0; i--) list.add(value.substring(0, i));
		return list;
	}
	
	
	/**
	 *  Takes a map of substrings to indices and returns a map of similar substrings to indices.
	 *  Highly inefficient prototype implementation due to deeply nested loops
	 * @param Map of substrings to indices
	 * @return Map of similar substrings to indices
	 */
	public static Map<String, List<Integer>> findSimilarStrings(Map<String, List<Integer>> map ) {
		Map<String, List<Integer>> result = new TreeMap<String, List<Integer>>();
		
		for(Map.Entry<String, List<Integer>> entry: map.entrySet()){
			
			for(String keyToTest: valueToString(entry.getKey())){ // get all substrings
			
				for(Map.Entry<String, List<Integer>> entry2: map.entrySet()){ // compare each of the substrings against the keys in the map
					String keyInMap = entry2.getKey();
				
					if( keyToTest.length() >= 3 &&  
						keyToTest.length() == keyInMap.length()){
						
						double diff = getDifference(keyToTest, keyInMap);
						if( diff > 0 && diff <= 0.125){
							List<Integer> intList = new ArrayList<Integer>();
							Set<Integer> intSet = new TreeSet<Integer>();
							intSet.addAll(entry.getValue());
							intSet.addAll(entry2.getValue());
							intList.addAll(intSet);
							result.put(keyToTest, intList);
						}
					}
				}
			}
		}	
		return result;
	}
}
