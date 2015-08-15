package rhythm.analysis.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

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
	private Random rand = new Random();
	
	private Map<Integer, Integer> colourMap = new HashMap<Integer, Integer>(); // Maps characters to colours.  
	private Arc_viewer arcViewerParent;
	private Rhythm_controller controller;
	
	
	Map<List<Integer>, Integer> usedRegion;
	
	public Text_viewer(Arc_viewer arcViewerParent, Rhythm_controller controller){
		this.arcViewerParent = arcViewerParent;	
		this.controller = controller;
		usedRegion = new TreeMap<List<Integer>, Integer>();
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
		if(this.controller.getModelString().length() != 0)  drawText();
	}
	
	
	public void drawText(){
		int currentChar = 20;
		int currentline = lineHeight;
		char[] charArray = this.controller.getModelString().toCharArray();
		int left = arcViewerParent.getleftSlider();
		int right = arcViewerParent.getRightSlider();
		
		for(int i = left; i <= right ;i ++){	
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
	
	
	private int getRandomNumber(int upperLimit){
		return rand.nextInt(upperLimit) + 1;
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
		usedRegion = new TreeMap<List<Integer>, Integer>();
		//map from string region to colour
		
		//int colour = getRandomNumber(255);
		
		for(List<Integer> matchingRegion: this.controller.getMatchingStrings()){
			System.out.println("*****************");
			System.out.println(matchingRegion);
			
			
//			List<Integer> arc1Region = getSequenceAsList(matchingRegion.get(0), matchingRegion.get(1));
//			List<Integer> arc2Region = getSequenceAsList(matchingRegion.get(2), matchingRegion.get(3));
			
			int colour = 1;
			
			
//			for(Integer next: arc1Region){
//					colourMap.put(next, colour);
//			}
//				
//			for(Integer next: arc2Region){
//				colourMap.put(next, colour);
//			}	
//		
			
		}
		}
		
	
//	private List<Integer> getSequenceAsList(int from, int to){
//		List<Integer> sequenceList = new ArrayList<Integer>();
//		for(int i = from; i <= to; i ++) sequenceList.add(i);
//		return sequenceList;
//	}
//	
//	
//	private Integer getColour(List<Integer> newRegion){
//		
//		for(Map.Entry<List<Integer>,Integer> next : usedRegion.entrySet()){
//			Set<Integer> intersection = new HashSet<Integer>(next.getKey());
//			intersection.retainAll(newRegion);
//			if(! intersection.isEmpty()){
//				return next.getValue();
//			} 
//		}
//		int newColour = getRandomNumber(255);
//		this.usedRegion.put(newRegion, newColour);
//		return newColour;
//	}


	
	@Override
	public void update() {
		setColourMap();
	}	
}
