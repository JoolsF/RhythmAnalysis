package rhythm.performance;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class NaiveSubstringFinder {

	public static Map<String, Set<Integer>> enumerateSubstrings(String str){
		Map<String, Set<Integer>> substringMap =  new TreeMap<String, Set<Integer>>();;
		for(int i = 0; i <= str.length() -1; i ++){
			for(int j = str.length(); j > i; j --){
				String substring = str.substring(i,j);
				if(substringMap.get(substring) == null) substringMap.put(substring, new HashSet<Integer>());
				substringMap.get(substring).add(i);
			}
		}
		return substringMap;
	}
	
	
}
