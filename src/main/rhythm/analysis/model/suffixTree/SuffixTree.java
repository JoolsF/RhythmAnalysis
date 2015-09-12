package rhythm.analysis.model.suffixTree;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rhythm.analysis.control.RhythmController;



/**
 * SuffixTree class
 * Container class for the suffix tree.  Contains the root Node and a RhythmController field to connect the suffix tree to the model
 * Also controls the process of adding strings to the suffix tree
 * 
 * @author Julian Fenner 
 */

public class SuffixTree {
	
	private Node root;
	private String string;
	private int numPulses; 
	private int minSubstrLength;
	private RhythmController controller;
	
	
	/*-----------------------------------------------------------------------------------------
	 * Constructors
	 *----------------------------------------------------------------------------------------*/
	
	public SuffixTree(){
		this.string = "";
		this.root = new NodeRoot();
		this.minSubstrLength = 1;
		this.numPulses = 8;
	}
	
	public SuffixTree(RhythmController controller) {
		this();
		this.controller = controller;
	}
		

	/**
	 * Takes a string argument and iterates through all substrings and corresponding indices adding them to root node
	 * @param the string to be added
	 * @return void
	 */
	public void addString(String str){		
		int startFrom = this.string.length(); // Allows for updating of suffix tree if further strings are added onto the end of initial string 
		String newString = this.string + str;
		this.string = newString;	
		
		for(int i = startFrom; i < newString.length(); i++){			
			for(int index = 0; index <= i; index++){
//				debugTrace("------------------------\nNODE TO ADD: " + newString.substring(index, i+1), index);
				root.addSubstring(newString.substring(index, i+1), index);
//				debugTrace("ADDED TO TREE: " + newString.substring(index, i+1), index);
			}	
//			debugTrace("NODE TO ADD: $",(i+1));
			root.addSubstring("$", i+1);
//			debugTrace("ADDED TO TREE: $", (i+1));
		}
	}
	
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/

	/**
	 * Gets the string field of the suffix tree
	 * @return the whole string represented by the suffix tree
	 */
	public String getString(){
		return this.string;
	}
	
	/**
	 * Sets the SuffixTree class's controller
	 * @param the suffix tree's controller
	 */
	public void setController(RhythmController rhythm_controller) {
		this.controller = rhythm_controller;
		
	}
	
	/**
	 * Gets the number of pulses per cycle in the data
	 * @return the number of pulse.
	 */
	public int getNumPulses(){
		return numPulses;
	}
	
	/**
	 * sets the number of pulses per cycle in the data.
	 * @param the number of pulses.
	 */
	public void setNumPulses(int numPulses){
		this.numPulses = numPulses;
	}
	
	/**
	 * Returns the value of every node in the in the suffix tree as a list.
	 * @return a list of strings
	 */
	public List<String> getSubstringList(){
		return this.root.nodesToList();
	}
	
	/**
	 * Returns the suffix tree in descending order of key length
	 * @return a map with  the key being the substring and the value being the list of indices at which the substring occurs
	 */
	public Map<String, List<Integer>> getSubstringMap(){
		Map<String, List<Integer>> substringMap = this.root.nodesToMap(new TreeMap<String, List<Integer>>(getComparator()));
		substringMap.remove("$");
		return substringMap;
	}
	
	/**
	 * Get the suffix tree's root node
	 * @return the suffix tree's root node 
	 */
	public Node getTree(){
		return this.root;
	}
	
	/**
	 * sets the minimum substringLength of the suffix tree
	 * @param minSubStrLength
	 */
	public void setMinSubstrLength(int minSubStrLength){
		//must be > 1
		this.minSubstrLength = minSubStrLength;
	}
	
	
	/**
	 * @return int 
	 */
	public int getMinSubstrLength(){
		return this.minSubstrLength;
	}
	
	
	private Comparator<String> getComparator(){
		Comparator<String> comp = new Comparator<String>(){
			@Override
			public int compare(String s1, String s2){
				if(s1.length() > s2.length()){
					return -1;
				} else if(s1.length() < s2.length()){
					return 1;
				} else {
					return s1.compareTo(s2);
				}	
			}
		};
		return comp;	
	}
	
	private void debugTrace(String str, int index){		
		System.out.println(str +"("+index+")" + "\n");
	}
	
}