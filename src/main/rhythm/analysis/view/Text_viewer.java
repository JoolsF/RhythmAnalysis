package rhythm.analysis.view;

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
	
	
	public void setup() {
		size(screenWidth, screenHeight);	
		f = createFont("Georgia",fontSize,true);
	    textFont(f);
	    fill(0); //set colour black
	    colorMode(HSB);	
	}
	
	public void draw(){
		background(128);
		smooth();
		
		
		String[] tStr = testString(1000);
		int currentChar = 20;
		int currentline = lineHeight;
		float c = 0;
		for(int i = 0; i < tStr.length ;i ++){
			
			//Colour cycler
			if (i % 255 == 0) { 
			  c = 0; 
			} else { 
			  c++;
			}
			
			if(i % 10 == 0){
				pushStyle();
				fill(c,255,255);
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
	
	public String[] testString(int size){
		String[] strArray = new String[size];
		for(int i = 0; i < size; i++){
			strArray[i] = "x";
		}
		return strArray;
		
	}
}
