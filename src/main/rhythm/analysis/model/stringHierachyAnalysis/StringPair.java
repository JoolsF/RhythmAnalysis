package rhythm.analysis.model.stringHierachyAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper class for StringHierarchyAnalyser.  StringPair represents a pair of string for comparison.
 * Analyses whether two strings conform to author's definition of string hierarchy as set out in project.
 * 
 * @author Julian Fenner
 */
public class StringPair {

	private List<Integer> string1;
	private List<Integer> string2;
	private List<Integer> stringSpan;
	private int subStrLength;
	private String subStr;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
	public StringPair(int string1start, int string2start, int subStrLength, String subStr){
		int arc1end = (string1start + subStrLength -1);
		int arc2end = (string2start + subStrLength -1);

		this.subStr = subStr;
		
		this.string1 = getSequenceAsList(string1start, arc1end); 
		this.string2 = getSequenceAsList(string2start, arc2end);
		this.stringSpan = getSequenceAsList(string1start, arc2end);
		this.subStrLength = subStrLength;	
	}
	
	
	/**
	 * Checks whether a string pair is valid
	 * @param the stringPair to be compared with this
	 * @return true if the StringPair argument passes the test else false
	 */
	public boolean stringValid(StringPair that){	
	if(that.subStrLength > this.subStrLength || this.subStr.equals(that.subStr) ){	
			return true;
		} else if (this.isSupersetOf(that)){
		  return true;	
		} else if(noIntersection(that)) {
		  return true;
		} else {
		  return false;
		}
	}
	
		
	private boolean noIntersection(StringPair that){
		Set<Integer> intersection1 = new HashSet<Integer>(this.string1);
		intersection1.addAll(this.string2);

		Set<Integer> intersection2 = new HashSet<Integer>(that.string1);
		intersection2.addAll(that.string2);
		intersection1.retainAll(intersection2);		
		if(intersection1.isEmpty()){
			return true;
		} else {
			return false;
		}		
	}
	
	private boolean isSupersetOf(StringPair that){
		Set<Integer> difference1 = new HashSet<Integer>(that.stringSpan);
		Set<Integer> difference2 = new HashSet<Integer>(that.stringSpan);
		difference1.removeAll(this.string1);
		difference2.removeAll(this.string2);
		
		if(difference1.isEmpty() || difference2.isEmpty()){
			return true;
		} else {
			return false;
		}
	}
	private List<Integer> getSequenceAsList(int from, int to){
		List<Integer> sequenceList = new ArrayList<Integer>();
		for(int i = from; i <= to; i ++) sequenceList.add(i);
		return sequenceList;
	}
	
	private void debugPrint(String name, StringPair ap){
		System.out.println();
		System.out.println("******************");
		System.out.println(name);
		System.out.println("String " + ap.subStr);
		System.out.println("stringSpan " + ap.stringSpan);
		System.out.println("string1 " + ap.string1);
		System.out.println("string2 " + ap.string2);
		System.out.println();		
	}
	
}
