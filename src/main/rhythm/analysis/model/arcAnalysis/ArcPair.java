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
	
	// TO DO - convert to compareTo
	public boolean arcValid(ArcPair that){
		
		System.out.println();
		System.out.println("******************");
		System.out.println("This string " + this.subStr);
		System.out.println("This span " + this.arcSpan);
		System.out.println("This arc1 " + this.arc1);
		System.out.println("This arc2 " + this.arc2);
		System.out.println();
		
		System.out.println("That string " + that.subStr);
		System.out.println("That span " + that.arcSpan);
		System.out.println("That arc1 " + that.arc1);
		System.out.println("That arc2 " + that.arc2);
		
		
		
		
	if(that.subStrLength > this.subStrLength
			|| this.subStr.equals(that.subStr) 
			  ){
		//To see why &&... needed try 1212 3412 3412 12 as input (entering in these stages) 
			// with and without it the right && clause	
			System.out.println("Greater than or equal to");
			// if the arc being compared is same size of greater than then true 
			return true;
		} 	else  if(noIntersection(that)) {
		System.out.println("no intersection");
		// the arc regions don't overlap at all
		return true;
	  } else if (this.isSupersetOf(that)){
			System.out.println("valid child");
			// if arc being compared is smaller than this then the arcpair's span and there is an intersection, that's arcSpan 
			//must be contained within either arc1 of arc2 of this
			return true;	
		} else {
			System.out.println("rejected");
			return false;
		}
	}
	
	public boolean noIntersection(ArcPair that){
		Set<Integer> intersection = new HashSet<Integer>(this.arcSpan);
		intersection.retainAll(that.arcSpan);
		if(intersection.isEmpty()){
			return true;
		} else {
			return false;
		}

		
		
//		//BUG 12ABAB12 AB(try adding in 2 stages
//		Set<Integer> intersection1 = new HashSet<Integer>(this.arc1);
//		Set<Integer> intersection2 = new HashSet<Integer>(this.arc2);
//		Set<Integer> intersection3 = new HashSet<Integer>(this.arcgap);
//		Set<Integer> intersection4 = new HashSet<Integer>(this.arcgap);
//
//		
//		
//		intersection1.retainAll(that.arc1);
//		intersection2.retainAll(that.arc2);
//		
//		intersection3.retainAll(that.arc1);
//		intersection4.retainAll(that.arc2);		
//		if(intersection1.isEmpty() || intersection2.isEmpty()){
//			//return true
//			
//			if(intersection3.isEmpty() && intersection4.isEmpty()){
//				return true;
//			} else {
//				return false;
//			}
//					
//		} else {
//			return false;
//		}
		
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
	
}
