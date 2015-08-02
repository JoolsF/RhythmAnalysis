package rhythm.analysis.control;

import java.util.ArrayList;
import java.util.List;

import rhythm.analysis.model.Rhythm_model;
import rhythm.analysis.view.Main_viewer;
import rhythm.analysis.view.Observer;

/**
 * Controller
 * @author Julian Fenner
 *
 */

public class Rhythm_controller {
	
	//private Main_viewer viewer;
	private Rhythm_model model;
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	
	/**
	 * constructor
	 */
	public Rhythm_controller(){	
		//create the model
		model = new Rhythm_model(this);	
//		//TO DO remove dummy data
		model.setString("1010");
	}
	
	public void notifyAllObservers(){
	      for (Observer observer : observers) {
	         observer.update();
	   }
	}
	
	public void attach(Observer observer){
	      observers.add(observer);		
	   }
	
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	public String getModelString(){
		return model.getString();
	}
	
	public List<String> getTreeAsList(){
		System.out.println(this.model.getTree().nodesToList());
		return this.model.getTree().nodesToList();
	}
	
	public int getNumPulses(){
		return this.model.getNumPulses();
	}
	
	public int[][] getMatchingStrings(){
		//TEST DATA
		int[][] nodePairs = new int[5][4]; // [number or pairs][nodes per pair]
		 nodePairs[0][0] = 0;
		 nodePairs[0][1] = 1;
		 nodePairs[0][2] = 2;
		 nodePairs[0][3] = 3;
		 
		 nodePairs[1][0] = 4;
		 nodePairs[1][1] = 6;
		 nodePairs[1][2] = 8;
		 nodePairs[1][3] = 10;
		 
//		 nodePairs[2][0] = 15;
//		 nodePairs[2][1] = 18;
//		 nodePairs[2][2] = 20;
//		 nodePairs[2][3] = 23;
//		 
//		 nodePairs[3][0] = 2;
//		 nodePairs[3][1] = 3;
//		 nodePairs[3][2] = 4;
//		 nodePairs[3][3] = 5;
//		 
//		 nodePairs[4][0] = 0;
//		 nodePairs[4][1] = 6;
//		 nodePairs[4][2] = 10;
//		 nodePairs[4][3] = 16;
//		 
		 return nodePairs;
		 	
	}
	
	//TO DO - add period length to main viewer which will be required by this method
		//note only applicable to binary data
//		private Integer[] getOnsets(int index){ //slider index
//			//TO DO - remove temporary var period
//			
//			int beginIndex = index;
//			if(index > numPulses ) beginIndex = index - (index % numPulses);
//			
//			char[] chars = this.arcViewerParent.getString().substring(beginIndex, beginIndex + numPulses).toCharArray();
//			List<Integer> result = new ArrayList<Integer>();
//			
//			for(char next: chars){
//				if(next =='1'){
//					result.add(beginIndex);
//				}
//				beginIndex += 1;
//			}
//			return result.toArray(new Integer[result.size()]);
//			
//		}
	

		
	
	public void updateTree(String str){
		this.model.addString(str);
		notifyAllObservers();
	}
	
	public void resetModel() {
		model.reset();
		notifyAllObservers();
	}
	
	public void createNewTree(String str){
		notifyAllObservers();
		// CHECK MORE DEPTH 2 TREES THEN TRY DEPTH 3. 
		
		// WORKING UP TO AB11AA
		// WORKING 112233
		//
		// WORKING AB11001
		// NOT WORKING AB110011AB
		// NOT WORKING WORKING 11001111
		// NOT WORKING 110011110 
		
		//model = new Rhythm_model(); //trace construction debug output carefully
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

	
	
	
	

}
