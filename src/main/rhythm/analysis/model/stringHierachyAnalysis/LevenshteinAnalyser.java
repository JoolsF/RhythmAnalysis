package rhythm.analysis.model.stringHierachyAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import rhythm.analysis.control.RhythmController;

/**
 * 
 * Utility class for Arc Analysis containing one public static method getDifference. 
 * 
 *
 */
public class LevenshteinAnalyser {
	
	
	private static double getDifference(String src, String arc){
		double difference = StringUtils.getLevenshteinDistance(src, arc);
		return (difference / (src.length()));
		
	}
	
	private static List<String> valueToString(String value){
		List<String> list = new ArrayList<String>();
		char[] charArray = value.toCharArray();
		
		for(int i = charArray.length -1; i > 0; i--) list.add(value.substring(0, i));
			
		return list;
	}
	
	// Make sure not intersecting
	// If score below a certain threshold then merge indices together (using set so not keeping dupes)
	
	public static Map<String, List<Integer>> findSimilarStrings(Map<String, List<Integer>> map ) {
		Map<String, List<Integer>> result = new TreeMap<String, List<Integer>>();
		
		for(Map.Entry<String, List<Integer>> entry: map.entrySet()){
			
			for(String keyToTest: valueToString(entry.getKey())){ // get all substrings
			
				for(Map.Entry<String, List<Integer>> entry2: map.entrySet()){ // compare each of the substrings against the keys in the map
					String keyInMap = entry2.getKey();
				
					if( keyToTest.length() >= 3 &&  
						keyToTest.length() == keyInMap.length()){
						
						double diff = getDifference(keyToTest, keyInMap);
						if(diff <= 0.2 && diff > 0){
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
