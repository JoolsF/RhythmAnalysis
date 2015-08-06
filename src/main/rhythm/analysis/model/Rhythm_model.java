package rhythm.analysis.model;

import rhythm.analysis.control.Rhythm_controller;



/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
/*
 * TO REFACTOR - Whole package....
 *  1 Convert child nodes arraylists into hashmaps 
 * 	Means order of nodes won't matter i.e $ last and no need to iterate through children
 * 
 *  2 Replace all printline methods with logging
 *  
 *  3. Add exception handling
 */

public class Rhythm_model {
	
	private Node root;
	private String string;
	//TO DO, set from ui
	private int numPulses = 8; 
	private Rhythm_controller controller;
	
	
	/*-----------------------------------------------------------------------------------------
	 * Constructors
	 *----------------------------------------------------------------------------------------*/
	
	public Rhythm_model(){
		this.string = "";
		this.root = new NodeRoot();
	}
	
	
	public Rhythm_model(Rhythm_controller controller) {
		this();
		this.controller = controller;
	}
		
	
	public void reset(){
		this.string = "";
		this.root = null; //nullifying current object.
		this.root = new NodeRoot();
	}
	
	public void addString(String str1){		
		//allows for updating of string
		int startFrom = this.string.length(); 
		String newString = this.string + str1;
		this.string = newString;	
		
		for(int i = startFrom; i < newString.length(); i++){			
			for(int index = 0; index <= i; index++){
				System.out.println("NODE TO ADD: " + newString.substring(index, i+1) + "(" +index+")");
				root.addString(newString.substring(index, i+1), index);
				
			}	
			root.addString("$", i+1);
			System.out.println("$ " + (i+1));
			System.out.println("%% END OF SUBSTRING %%");

		}	
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	public Node getTree(){
		return this.root;
	}

	public String getString(){
		return this.string;
	}
	public void setController(Rhythm_controller rhythm_controller) {
		this.controller = rhythm_controller;
		
	}
	
	public int getNumPulses(){
		return numPulses;
	}
	
}
