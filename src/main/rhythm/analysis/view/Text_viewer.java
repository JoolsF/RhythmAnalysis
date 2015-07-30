package rhythm.analysis.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.gicentre.utils.multisketch.EmbeddedSketch;

import processing.core.PFont;

public class Text_viewer  extends EmbeddedSketch{

	private static final long serialVersionUID = 1L;
	private final int screenWidth = 900;
	private final int screenHeight = 600;
	private final int screenBorder = 50;
	
	private PFont f;
	private int fontSize = 20;
	private int lineHeight = fontSize;
	private int characterSpacing = 15;
	Random rand = new Random();
	
	
	Map<Integer, Integer> colourMap = new HashMap<Integer, Integer>(); // Maps characters to colours.  
	
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
		
		
		String[] tStr = testString(1000);
		int currentChar = 20;
		int currentline = lineHeight;
	
		for(int i = 0; i < tStr.length ;i ++){
			
			
			
			if(colourMap.get(i) != null){
				pushStyle();
				fill(colourMap.get(i),255,255);
				text(tStr[i], currentChar, currentline);
				popStyle();
			} else {
				text(tStr[i], currentChar, currentline);	
			}
			
			currentChar += characterSpacing;
			if((i != 0) && (i % 50 == 0)) {
				currentline += lineHeight;
				currentChar = 20;
			}
		}
		
	}
	
	private String[] testString(int size){
		String[] strArray = new String[size];
		for(int i = 0; i < size; i++){
			strArray[i] = "x";
		}
		return strArray;
		
	}
	
	/**
	 * Takes a 2d array representing pairs of matching words
	 * Each element contains an array of length 3 with {a,b,l} integers
	 * a is position of character a
	 * b is position of character b
	 * l is length of words
	 * 
	 */
	private void setColourMap(){ //int[][] pairs arg
		int colour = getRandomNumber(255);
		
		//TEST DATA
		colourMap.put(3, colour);
		colourMap.put(4, colour);
		colourMap.put(5, colour);
		
		colourMap.put(20, colour);
		colourMap.put(21, colour);
		colourMap.put(22, colour);
		
		int colour2 = getRandomNumber(255);
		colourMap.put(33, colour2);
		colourMap.put(34, colour2);
		colourMap.put(35, colour2);
		colourMap.put(36, colour2);
		
		colourMap.put(40, colour2);
		colourMap.put(41, colour2);
		colourMap.put(42, colour2);
		colourMap.put(43, colour2);
		//TEST DATA END
		
	}

	
	private int getRandomNumber(int upperLimit){
		return rand.nextInt(upperLimit) + 1;
	}
	
}
