package analysis.model;
//http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import analysis.Rhythm_controller;
import analysis.view.Rhythm_viewer;




/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
/*
 * TO REFACTOR
 * CHANGE OVER CHILD NODES FOR HASHMAPS 
 * 	Means order of nodes won't matter i.e $ last and no need to iterate through children
 */

public class Rhythm_model {
	
	private Node root;
	private Rhythm_controller controller = null;
	private Rhythm_viewer viewer = null;

	
	public Rhythm_model(){
		root = new NodeRoot();
	}
	
	public Rhythm_model(String str){
		this();
		addString(str);
	}
	
	public void addString(String str){		
		for(int i = 0; i < str.length(); i++){			
			for(int index = 0; index <= i; index++){
				System.out.println("NODE TO ADD: " + str.substring(index, i+1) + "(" +index+")");
				root.addString(str.substring(index, i+1), index);
				
			}	
			//$ added at the end of each substring iteration
			root.addString("$", i+1);
			System.out.println("$ " + (i+1));
			System.out.println("%% END OF SUBSTRING %%");
			System.out.println();
			System.out.println();
		}
		//update NodeValues after each string is added.
		
	}
	
	public Node getTree(){
		return this.root;
	}

	public void setController(Rhythm_controller r) {
		this.controller = r;
		
	}

	public void setViewer(Rhythm_viewer v) {
		this.viewer = v;
	}
	
	
	
	
	
}
