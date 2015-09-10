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
	public RhythmController(){
		this.suffixTree = new SuffixTree(this);	
		this.arcMin = 1;
		this.arcAnalyser = new StringHierarchyAnalyser(suffixTree);
	}
	
	
	public RhythmController(MainViewer mainViewer){	
		this();
		//create the model
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
		return this.suffixTree.getSubstringList();
	}
	
	public Map<String, List<Integer>> getTreeAsMap(){
		return this.suffixTree.getSubstringMap();
	}
	
	
	public int getNumPulses(){
		return this.suffixTree.getNumPulses();
	}
	
	public void setNumPulses(int numPulses){
		this.suffixTree.setNumPulses(numPulses);
		notifyAllObservers();
	}
	public List<List<Integer>> getMatchingStrings(){
		return this.arcAnalyser.getStringCoordinatesExactMatch(getTreeAsMap(), this.mainviewer.getArcFilterToggleValue());
	}
	
	public List<List<Integer>> getSimilarStrings(){
		if(this.mainviewer.getSimilarityToggleValue()){
			return this.arcAnalyser.getStringCoordinatesInexactMatch(getTreeAsMap(), this.mainviewer.getArcFilterToggleValue());
			
		}	else {
			return new ArrayList<List<Integer>>();
		} 	
	}
	
	
	public void updateTree(String str){
		this.suffixTree.addString(str);
		this.suffixTree.getTree().processTree("");
		notifyAllObservers();
	}
	

	public void setArcMin(int arcMin){
		this.arcMin = arcMin;
	}
	
	public int getArcMin(){
		return this.arcMin;
	}
	

}
