package rhythm.analysis.model.arcAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcPair {

	private List<Integer> arc1;
	private List<Integer> arc2;
	private List<Integer> arcgap;
	private List<Integer> arcSpan;
	private int subStrLength;
	private String subStr;
	
	public ArcPair(int arc1start, int arc2start, int subStrLength, String subStr){
		int arc1end = (arc1start + subStrLength -1);
		int arc2end = (arc2start + subStrLength -1);
		
		this.subStr = subStr;
		
		this.arc1 = getSequenceAsList(arc1start, arc1end); 
		this.arc2 = getSequenceAsList(arc2start, arc2end);
		this.arcgap = getSequenceAsList(arc1end, arc2start);
		this.arcSpan = getSequenceAsList(arc1start, arc2end);
		this.subStrLength = subStrLength;
		
	}
	
	private List<Integer> getSequenceAsList(int from, int to){
		List<Integer> sequenceList = new ArrayList<Integer>();
		
		for(int i = from; i <= to; i ++) sequenceList.add(i);
		
		return sequenceList;
	}
	
	
	public boolean arcValid(ArcPair that){
		
		debugPrint("this", this);
		debugPrint("that", that);
		//try 1212 3412 3412 12 as input (entering in these stages) 
		if(that.subStrLength > this.subStrLength || this.subStr.equals(that.subStr) ){	
			// if the arc being compared is same size of greater than then true
			System.out.println("Greater than or equal to");
			return true;
	  } else if (this.isSupersetOf(that)){
		  // if arc being compared is smaller than this then the arcpair's span and there is an intersection, that's arcSpan 
		  //must be contained within either arc1 of arc2 of this
		  System.out.println("valid child");
		  return true;	
//BUG HERE
	  } else if(noIntersection(that)) {
		  // the arc regions don't overlap at all return true;
		  System.out.println("no intersection");
		  return true;
	  } else {
		  System.out.println("rejected");
		  return false;
		}
	}
	
	/**
	 * Checks whether This' arc regions intersect with That's arc regions
	 * @param that
	 * @return
	 */
	public boolean noIntersection(ArcPair that){
		Set<Integer> intersection1 = new HashSet<Integer>(this.arc1);
		intersection1.addAll(this.arc2);

		Set<Integer> intersection2 = new HashSet<Integer>(that.arc1);
		intersection1.addAll(that.arc2);
		intersection1.retainAll(intersection2);		
		if(intersection1.isEmpty()){
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean isSupersetOf(ArcPair that){
		Set<Integer> difference1 = new HashSet<Integer>(that.arcSpan);
		Set<Integer> difference2 = new HashSet<Integer>(that.arcSpan);
		difference1.removeAll(this.arc1);
		difference2.removeAll(this.arc2);
		
		if(difference1.isEmpty() || difference2.isEmpty()){
			return true;
		} else {
			return false;
		}
	}
	
	private void debugPrint(String name, ArcPair ap){
		System.out.println();
		System.out.println("******************");
		System.out.println(name);
		System.out.println("String" + ap.subStr);
		System.out.println("arcspan " + ap.arcSpan);
		System.out.println("arc1 " + ap.arc1);
		System.out.println("arc2 " + ap.arc2);
		System.out.println();		
	}
	
}
