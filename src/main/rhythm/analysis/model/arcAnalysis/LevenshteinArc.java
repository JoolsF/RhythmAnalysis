package rhythm.analysis.model.arcAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import rhythm.analysis.control.Rhythm_controller;

/**
 * 
 * Utility class for Arc Analysis containing one public static method getDifference. 
 * 
 *
 */
public class LevenshteinArc {
	
	
	public static double getDifference(String src, String arc){
		double difference = StringUtils.getLevenshteinDistance(src, arc);
		return (difference / src.length());
	}
	
	
	
	
	private static List<String> valueToString(String value){
		List<String> list = new ArrayList<String>();
		char[] charArray = value.toCharArray();
		
		for(int i = charArray.length -1; i >= 0; i--) list.add(value.substring(0, i));
			
		return list;
	}
	
	public static void /*Map<String, List<Integer>>*/ findSimilarStrings(Map<String, List<Integer>> map ) {
		for(Map.Entry<String, List<Integer>> entry: map.entrySet()){
			for(String keyToTest: valueToString(entry.getKey())){ // get all substrings
				for(String keyInMap: map.keySet()){ // compare each of the substrings against the keys in the map
					System.out.println("hi");
				}
			}
		}
	}
	
	
}
