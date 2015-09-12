package rhythm.analysis.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rhythm.analysis.model.stringHierachyAnalysis.StringHierarchyAnalyser;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.MainViewer;
import rhythm.analysis.view.Observer;

/**
 * RhythmController class
 * 
 * Controls communication between the model and the view
 * @author Julian Fenner
 */

public class RhythmController {
	
	private SuffixTree suffixTree;
	private List<Observer> observers;
	private MainViewer mainviewer;
	private int arcMin;
	private StringHierarchyAnalyser stringHierarchyAnalyser;
	

	/*-----------------------------------------------------------------------------------------
	 * Constructors
	 *----------------------------------------------------------------------------------------*/
	public RhythmController(){
		this.observers = new ArrayList<Observer>();
		this.suffixTree = new SuffixTree(this);	
		this.arcMin = 1;
		this.stringHierarchyAnalyser = new StringHierarchyAnalyser(suffixTree);
	}
	
	public RhythmController(MainViewer mainViewer){	
		this();
		//create the model
		this.mainviewer = mainViewer;
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Observer methods
	 *----------------------------------------------------------------------------------------*/
	/**
	 * Updates all observers attached to the observers list 
	 */
	public void notifyAllObservers(){
	      for (Observer observer : observers) {
	         observer.update();
	   }
	}
	
	/**
	 * Attaches object implementing Observer interface to the controller
	 * @param the observer to be attached
	 */
	public void attach(Observer observer){
	      observers.add(observer);		
	   }
	
	
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	/**
	 * Gets the string represented by the suffix tree
	 * @return the string represented by the suffix tree
	 */
	public String getModelString(){
		return suffixTree.getString();
	}
	
	/**
	 * Gets the suffix tree substrings and indices as a list
	 * @return List of String representing the suffix trees's substrings and indices. 
	 */
	public List<String> getTreeAsList(){
		return this.suffixTree.getSubstringList();
	}
	
	/**
	 * Gets the suffix tree substrings and indices as a Map
	 * @return a Map of String to Integer List representing the suffix tree's substrings and indices
	 */
	public Map<String, List<Integer>> getTreeAsMap(){
		return this.suffixTree.getSubstringMap();
	}
	
	/**
	 * Gets the number of pulses per cycle in the data
	 * @return number of pulses per cycle in the data
	 */
	public int getNumPulses(){
		return this.suffixTree.getNumPulses();
	}
	
	/**
	 * Sets the number of pulses per cycle in the data
	 * @param int representing number of pulses per cycle in the data
	 */
	public void setNumPulses(int numPulses){
		this.suffixTree.setNumPulses(numPulses);
		notifyAllObservers();
	}
	
	/**
	 * Gets the matching strings filtered though StringHierarchyAnalyser
	 * @return List of Integer List representing the matching strings filtered though StringHierarchyAnalyser
	 */
	public List<List<Integer>> getMatchingStrings(){
		return this.stringHierarchyAnalyser.getStringCoordinatesExactMatch(getTreeAsMap(), this.mainviewer.getArcFilterToggleValue());
	}
	
	/**
	 * Gets the similar strings filtered though StringHierarchyAnalyser
	 * @return List of Integer List representing the similar strings filtered though StringHierarchyAnalyser
	 */
	public List<List<Integer>> getSimilarStrings(){
		if(this.mainviewer.getSimilarityToggleValue()){
			return this.stringHierarchyAnalyser.getStringCoordinatesInexactMatch(getTreeAsMap(), this.mainviewer.getArcFilterToggleValue());	
		}	else {
			//TO DO - Refactor so that this line is not necessary
			return new ArrayList<List<Integer>>();
		} 	
	}
	
	/**
	 * Calls SuffixTree's addString method followed by its processTree method
	 * @param the string to be added to the suffix tree
	 */
	public void updateTree(String str){
		this.suffixTree.addString(str);
		this.suffixTree.getTree().processTree("");
		notifyAllObservers();
	}
	
	/**
	 * Sets the minimum arc to be displayed in the view classes
	 * @param the minimum arc to be displayed in the view classes
	 */
	public void setArcMin(int arcMin){
		this.arcMin = arcMin;
	}
	
	/**
	 * Gets the minimum arc to be displayed in the view classes
	 * @return the minimum arc to be displayed in the view classes
	 */
	public int getArcMin(){
		return this.arcMin;
	}

}
