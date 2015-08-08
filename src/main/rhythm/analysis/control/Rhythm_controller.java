package rhythm.analysis.control;

import java.util.ArrayList;
import java.util.List;

import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.Main_viewer;
import rhythm.analysis.view.Observer;

/**
 * Controller
 * @author Julian Fenner
 *
 */

public class Rhythm_controller {
	
	//private Main_viewer viewer;
	private SuffixTree model;
	private List<Observer> observers = new ArrayList<Observer>();
	
	
	
	/**
	 * constructor
	 */
	public Rhythm_controller(){	
		//create the model
		model = new SuffixTree(this);	
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
		//System.out.println(this.model.getTree().nodesToList());
		return this.model.getSubStringList();
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
	

	
	
	public void updateTree(String str){
		this.model.addString(str);
		notifyAllObservers();
	}
	
	public void resetModel() {
		model.reset();
		notifyAllObservers();
	}
	

}
