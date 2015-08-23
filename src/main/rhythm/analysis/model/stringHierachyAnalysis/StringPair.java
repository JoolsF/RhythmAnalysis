package rhythm.analysis.model.stringHierachyAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringPair {

	private List<Integer> string1;
	private List<Integer> string2;
	private List<Integer> stringGap;
	private List<Integer> stringSpan;
	private int subStrLength;
	private String subStr;
	

	
	public StringPair(int string1start, int string2start, int subStrLength, String subStr){
		int arc1end = (string1start + subStrLength -1);
		int arc2end = (string2start + subStrLength -1);
		
		this.subStr = subStr;
		
		this.string1 = getSequenceAsList(string1start, arc1end); 
		this.string2 = getSequenceAsList(string2start, arc2end);
		this.stringGap = getSequenceAsList(arc1end, string2start);
		this.stringSpan = getSequenceAsList(string1start, arc2end);
		this.subStrLength = subStrLength;
		
	}
	
	private List<Integer> getSequenceAsList(int from, int to){
		List<Integer> sequenceList = new ArrayList<Integer>();
		
		for(int i = from; i <= to; i ++) sequenceList.add(i);
		
		return sequenceList;
	}
	
	
	public boolean stringValid(StringPair that){	
//		debugPrint("this", this);
//		debugPrint("that", that);	
		

		if(that.subStrLength > this.subStrLength || this.subStr.equals(that.subStr) ){	
			// if the string being compared is same size of greater than then true
			return true;
		} else if (this.isSupersetOf(that)){
		  // if the string being compared is smaller than this then the string pair's span and there is an intersection, That's stringSpan 
		  //must be contained within either string1 of string2 of this
		  return true;	
		} else if(noIntersection(that)) {
		  // the arc regions don't overlap at all return true;
		  return true;
		} else {
		  return false;
		}
	}
	
	/**
	 * Checks whether This' string region intersect with That's string region
	 * @param that
	 * @return
	 */
	public boolean noIntersection(StringPair that){
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
	
	public boolean isSupersetOf(StringPair that){
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
