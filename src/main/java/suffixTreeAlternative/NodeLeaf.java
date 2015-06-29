package suffixTreeAlternative;

import suffixTree.NodeImpl;


public class NodeLeaf implements Node, InnerLeaf {

	String string;
	int stringIndex;
	Node parent;
	
	public NodeLeaf(String string, int StringIndex, Node parent){
		
		this.string = string;
		this.stringIndex = stringIndex;
		this.parent = parent;
	}

	@Override
	public void addString(String string, int index) {
		if(this.string.equals("$")){
			//BASE CASE 
			//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
			//children will be sorted so that "$" for any given list of children will always be at the end.
			//TO DO - UPDATE INDEX TOO FOR SAFETY?
			this.string = string;
			this.stringIndex = index;
			return;
		} else if (this.string.equals(string)){
			//DO SOMETHING
		} else if(this.thisHasAPrefixOf(string)){
			String prefix =  this.string.substring(0, string.length());
			String suffix = this.string.substring(string.length(), this.string.length());
// NEEDS TO GO BACK TO PARENT, GET DELETED AND HAVE A NONLEAF NODE PUT IN ITS PLACE
			
//				String prefix =  childNode.getSubString().substring(0, subStringToAdd.length());
//				String suffix = childNode.getSubString().substring(subStringToAdd.length(), childNode.getSubString().length());
//				childNode.addChild(new NodeImpl(suffix, childNode.getSubStringIndex()));
//				childNode.setSubString(prefix, -1);
//				childNode.addChild(new NodeImpl("$", subStringIndex));
//				return;


			
		}
		
	}

	@Override
	public String getString() {
		return this.string;
	}


}
