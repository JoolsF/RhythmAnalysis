package rhythm.analysis.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rhythm.analysis.model.stringHierachyAnalysis.StringHierarchyAnalyser;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.MainViewer;
import rhythm.analysis.view.Observer;

/**
 * Controller
 * @author Julian Fenner
 *
 */

public class RhythmController {
	
	private StringHierarchyAnalyser arcAnalyser;
	private SuffixTree suffixTree;
	private List<Observer> observers = new ArrayList<Observer>();
	
	private int arcMin;
	private boolean applyArcAnalysis;
	
	private MainViewer mainviewer;
	
	/**
	 * constructor
	 */
	public RhythmController(MainViewer mainViewer){	
		//create the model
		suffixTree = new SuffixTree(this);	
		this.arcMin = 1;
		this.arcAnalyser = new StringHierarchyAnalyser(suffixTree);
		this.mainviewer = mainViewer;
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
		return suffixTree.getString();
	}
	
	public List<String> getTreeAsList(){
		return this.suffixTree.getSubStringList();
	}
	
	public Map<String, List<Integer>> getTreeAsMap(){
		return this.suffixTree.getSubStringMap();
	}
	
	
	public int getNumPulses(){
		return this.suffixTree.getNumPulses();
	}
	
	public void setNumPulses(int numPulses){
		this.suffixTree.setNumPulses(numPulses);
		notifyAllObservers();
	}
	public List<List<Integer>> getMatchingStrings(){
		return this.arcAnalyser.getStringCoordinatesExactMatch(getTreeAsMap(), this.mainviewer.getToggleValue());	 	
	}
	
	public List<List<Integer>> getSimilarStrings(){
		return this.arcAnalyser.getStringCoordinatesInexactMatch(getTreeAsMap(), this.mainviewer.getToggleValue());	 	
	}
	
	
	public void updateTree(String str){
		this.suffixTree.addString(str);
		notifyAllObservers();
	}
	
	public void resetModel() {
		suffixTree.reset();
		notifyAllObservers();
	}
	
	public void setArcMin(int arcMin){
		this.arcMin = arcMin;
		notifyAllObservers();
	}
	
	public int getArcMin(){
		return this.arcMin;
	}
	

}
