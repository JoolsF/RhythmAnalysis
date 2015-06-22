package substringAlgorithms;

import java.util.List;

/**
 * Each internal has at least two children.
 */
public class NodeNonLeaf implements Node {
	//TO DO could be a map?
	private String subStringField;
	private Integer subStringIndex;
	//String subStringPrefix;
	private List<NodeNonLeaf> children;
	
	
	public NodeNonLeaf(String str, int subStringIndex){
		this.children = null;
		addSubString(subStringField,subStringIndex);
		
	}
	
	
	
	
	public void addSubString(String subStringToAdd, int subStringIndex){
		if (subStringToAdd.startsWith(subStringField)){
			//checking if the child's substring is a prefix of the substring field. i.e child's substring is a and substring is ab
			if(this.children == null ){
				updateSubString(subStringToAdd);
				return;
			}
		}else if(this.subStringField.startsWith(subStringToAdd)){
		//checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1 	
			/* This case turns a LEAF node where the substring to add is a prefix of this node's substring field value.  
			 * i.e
			 * 		   \
			 * 			\
			 * 			11 (2)  <- adding 1 to leaf
			 * 
			 * 			\
			 *           \
			 *            1
			 * 			 / \
			 * 			/   \
			 * 		null(3)  1(2)
			 * 		
			 * 
			 * 
			 * This requires 
			 * 1) var for matching prefix 
			 * 2) var for rest of string i.e suffix
			 * 3) adding a child leaf node which has the suffix as its value along with the index of the current node
			 * 4) current node 'converted' into non-leaf node by deleting its index (not applicable now) 
			 * 5) and setting its substring field to prefix
			 * 6) finally we need to give the other index of the prefix 
			 * 
			 * 
			 **/
			
			
			//1
			String prefix =  subStringField.substring(0, subStringToAdd.length());
			//2
			String suffix = subStringField.substring(subStringToAdd.length(), subStringField.length());
			//3
			children.add(new NodeNonLeaf(suffix, this.subStringIndex));
			//4
			this.subStringIndex = null;
			//5
			subStringField = prefix;
			//6
			children.add(new NodeNonLeaf("$", subStringIndex));
			
		
		} else {
			for(Node child: children) {
				child.addSubString(subStringToAdd, subStringIndex);
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
		/*
		for(Node child: children) {
			if (subString.startsWith(child.getSubString())){
				//checking if the child's substring is a prefix of the substring field. i.e child's substring is a and substring is ab
				
				
				
				if(child.getChildren()== null) { //i.e child this is leaf node
					child.updateSubString(subString);
					return;
				}
			} else if(child.getSubString().startsWith(subString)){
				//checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1 
				String prefix = child.getSubString().substring(0, subString.length());
				String suffix = child.getSubString().substring(subString.length(), child.getSubString().length());
				this.subString = prefix;
				//INITIALISE CHILDREN IF LEAF
				children.add(new NodeNonLeaf(this.subString, this.subStringIndex));
				
				
			}
		}
		//i.e you've iterated through all of the children and not found a substring match
		children.add(new NodeNonLeaf(subString,subStringIndex)); //CHECK THIS IS MAKING LEAD NODE
		*/
	

	@Override
	public List<Node> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateSubString(String newSubString){
		subStringField = newSubString;
		
	}
	
	public String getSubString(){
		return this.subStringField;
	}
}
