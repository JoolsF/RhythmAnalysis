package substringAlgorithms;

import java.util.List;

/**
 * Each internal has at least two children.
 */
public class NodeImpl {
	//TO DO could be a map?
	private String subString;
	private int subStringIndex;
	//String subStringPrefix;
	private List<NodeImpl> children;
	
	public NodeImpl(String str, int subStringIndex){
		addSubString(subString,subStringIndex);
		
	}
	
	public void updateSubString(String str){
		subString += str;
		
	}
	
	public String getSubString(){
		return this.subString;
	}
	//is this definitely needed?
	public void addSubString(String subString, int subStringIndex){
		if(children.isEmpty()){
			children.add(new NodeImpl(subString,subStringIndex));	
		} else {
			
			
		}
		
		
		//this.subString = subString;
		//this.subStringIndex = subStringIndex;
		
	}
	

}
