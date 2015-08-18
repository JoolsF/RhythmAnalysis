package rhythm.analysis.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.gicentre.utils.multisketch.EmbeddedSketch;
import org.gicentre.utils.multisketch.PopupWindow;

import controlP5.ControlP5;
import controlP5.Textarea;
import processing.core.PFont;
import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.arcAnalysis.ArcPair;

public class Text_viewer  extends EmbeddedSketch implements Observer{

	private static final long serialVersionUID = 1L;
	private final int screenWidth = 500;
	private final int screenHeight = 500;
	private final int screenBorder = 50;
	
	private PFont f;
	private int fontSize = 20;
	private int lineHeight = fontSize;
	private int characterSpacing = 15;
	//private Random rand = new Random();
	
	private Arc_viewer arcViewerParent;
	private Rhythm_controller controller;
	
	private Map<Integer, Integer> colourMap;
	//Map<List<Integer>, Integer> usedRegion;
	
	private int colour;
	
	
	public Text_viewer(Arc_viewer arcViewerParent, Rhythm_controller controller){
		this.arcViewerParent = arcViewerParent;	
		this.controller = controller;
		this.colourMap = new HashMap<Integer, Integer>(); // Maps characters to colours.
		this.colour = 0;
	}
	
	
	public void setup() {
		size(screenWidth, screenHeight);	
		f = createFont("Georgia",fontSize,true);
	    textFont(f);
	    fill(0); //set colour black
	    colorMode(HSB);	
	    setColourMap();
	}
	
	public void draw(){
		background(128);
		smooth();
		// TO DO - handle this case better where string length == 0
		if(this.controller.getModelString().length() > 0)  drawText();
	}
	
	
	public void drawText(){
		int currentline = lineHeight;
		char[] charArray = this.controller.getModelString().toCharArray();
		
		int left = arcViewerParent.getleftSlider();
		int right = arcViewerParent.getRightSlider();
		
		int currentChar = 20 + left;
		for(int i = left; i <= right; i ++){	
			if(colourMap.get(i) != null){
				pushStyle();
				fill(colourMap.get(i),255,255);
				text(charArray[i], currentChar, currentline);
				popStyle();
			} else {
				text(charArray[i], currentChar, currentline);	
			}
			
			currentChar += characterSpacing;
			if((i != 0) && (i % 30 == 0)) {
				currentline += lineHeight;
				currentChar = 20;
			}
		}
		
	}
	
	
	private int getColour(){
		this.colour = (this.colour + 50) % 255;
		return this.colour;
	}
	
	/**
	 * Takes a 2d array representing pairs of matching words
	 * Each element contains an array of length 3 with {a,b,l} integers
	 * a is position of character a
	 * b is position of character b
	 * l is length of words
	 * 
	 */
	private void setColourMap(){ 
		
		colourMap.clear();
		for(List<Integer> matchingRegion: this.controller.getMatchingStrings()){
			List<Integer> arc1Region = getSequenceAsList(matchingRegion.get(0), matchingRegion.get(1));
			List<Integer> arc2Region = getSequenceAsList(matchingRegion.get(2), matchingRegion.get(3));
			
			if(arc1Region.size() -1 >= this.controller.getArcMin() -1){
				Set<Integer> intersection = new TreeSet<Integer>(colourMap.keySet());
				intersection.retainAll(arc1Region);	
				int colour;
				
				if(intersection.isEmpty()){
					colour = getColour();
				} else {
					colour = intersection.iterator().next();;
				}
				
				for(Integer next: arc1Region) colourMap.put(next, colour);	
				for(Integer next: arc2Region) colourMap.put(next, colour);
			}
		}
	}
		
	
	private List<Integer> getSequenceAsList(int from, int to){
		List<Integer> sequenceList = new ArrayList<Integer>();
		for(int i = from; i <= to; i ++) sequenceList.add(i);
		return sequenceList;
	}
	

	
	@Override
	public void update() {
		setColourMap();
		redraw();
	}	
}
