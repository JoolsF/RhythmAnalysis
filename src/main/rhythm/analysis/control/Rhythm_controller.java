package rhythm.analysis.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rhythm.analysis.model.arcAnalysis.ArcAnalyser;
import rhythm.analysis.model.suffixTree.SuffixTree;
import rhythm.analysis.view.Observer;

/**
 * Controller
 * @author Julian Fenner
 *
 */

public class Rhythm_controller {
	
	private ArcAnalyser arcAnalyser;
	private SuffixTree suffixTree;
	private List<Observer> observers = new ArrayList<Observer>();
	
	private int arcMin;
	
	/**
	 * constructor
	 */
	public Rhythm_controller(){	
		//create the model
		suffixTree = new SuffixTree(this);	
		this.arcMin = 1;
		this.arcAnalyser = new ArcAnalyser(suffixTree);
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
	
	
	public int getNumPulses(){
		return this.suffixTree.getNumPulses();
	}
	
	public List<List<Integer>> getMatchingStrings(){
		return this.arcAnalyser.getArcCoordinates();	 	
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
