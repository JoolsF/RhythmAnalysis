package rhythm.analysis;

//controller

import java.util.List;

import rhythm.analysis.model.Rhythm_model;
import rhythm.analysis.view.Rhythm_viewer;




public class Rhythm_controller {
	
	Rhythm_viewer viewer = null;
	Rhythm_model model = null;
	
	
	/**
	 * constructor
	 */
	public Rhythm_controller(){
	}
	
	public void initAll(Rhythm_viewer v){
		//init viewer
		viewer = v;
		
		//create the model
		model = new Rhythm_model();
		
		//create references between MVC components
		model.setController(this);
		viewer.setModel(model);
		model.setViewer(viewer);
	}
	
	
	
	
	
	
	
	
	public void createNewTree(String str){
		// CHECK MORE DEPTH 2 TREES THEN TRY DEPTH 3. 
		
		// WORKING UP TO AB11AA
		// WORKING 112233
		//
		// WORKING AB11001
		// NOT WORKING AB110011AB
		// NOT WORKING WORKING 11001111
		// NOT WORKING 110011110 
		
		model = new Rhythm_model("AB11001"); //trace construction debug output carefully
		//suffixTree.getTree().printTree();
		
		
//		Map<String, List<Integer>> nodeMap = test.getTree().nodesToMap(new TreeMap<String, List<Integer>>());
//		for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
//    		System.out.println(value.getKey() + ": " + value.getValue());
//    	}
		
		
	
		
//		Node root = new NodeRoot();
//		List<InnerNode> children = new ArrayList<InnerNode>();
//		InnerNode nonLeaf = new NodeNonLeaf("a", 1, root, children);
//		
//		
//		InnerNode leafOther = new NodeLeaf("001111",0,nonLeaf);
//		InnerNode leafOther2 = new NodeLeaf("001111",0,nonLeaf);
//		InnerNode leaf$ = new NodeLeaf("$",6,nonLeaf);
//		children.add(leafOther);
//		//children.add(leafOther2);
//		children.add(leaf$);
//		
//		
//		System.out.println(leafOther.okToSplitNode('0'));
		
	}
	public List<String> getTreeAsList(){
		System.out.println(this.model.getTree().nodesToList());
		return this.model.getTree().nodesToList();
	}
	
	

}
