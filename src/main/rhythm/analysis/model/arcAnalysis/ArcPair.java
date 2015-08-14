package rhythm.analysis.model.arcAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcPair {

	private List<Integer> arc1;
	private List<Integer> arc2;
	private List<Integer> arcSpan;
	private int subStrLength;
	
	public ArcPair(int arc1start, int arc2start, int subStrLength){
		int arc1end = (arc1start + subStrLength -1);
		int arc2end = (arc2start + subStrLength -1);
		
		
		this.arc1 = getSequenceAsList(arc1start, arc1end); 
		this.arc2 = getSequenceAsList(arc2start, arc2end);
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
		if(that.subStrLength >= that.subStrLength){ 
			// if the arc being compared is same size of greater than then true 
			return true;
		} else if(noIntersection(that)) {
			// the arc regions don't overlap at all
			return true;
		} else if (this.arc1.contains(that.arcSpan) || this.arc2.contains(that.arcSpan)){
			// if arc being compared is smaller than this then the arcpair's span and there is an intersection, that's arcSpan 
			//must be contained within either arc1 of arc2 of this
			return true;	
		} else {
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
		
	}
	
}
