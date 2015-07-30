package rhythm.analysis.view;

import org.gicentre.utils.multisketch.EmbeddedSketch;
import g4p_controls.*;


import controlP5.ControlP5;
import controlP5.Textarea;
import processing.core.PVector;


/**
 * 
 * @author Julian Fenner
 * Shows two cycle (periods) in the data and compares their geoshape.
 *
 */
/*
 * REFERENCES
 * http://www.openprocessing.org/sketch/27952 for connecting points in a circle
 *
 */

//TO DO - Need to rotate circle -90 so that 0 is at 0 degrees.
public class Cycle_viewer extends EmbeddedSketch  {
	private static final long serialVersionUID = 1L;
	
	//TO DO - move to constructor
	private int numPulses = 20;
	private PVector[] points = new PVector[numPulses];
	private PVector[] charPoints = new PVector[numPulses];
	private float radius = 100;
	
		
	//Test data
	private int[] onsets = new int[]{};
	
	GTextArea txaSample;
	
	public Cycle_viewer(){	
	}
	

	 
	public void setup() {
		//TEST LINE
		setOnsets(new int[]{1,10,15});
		//TEST LINE
		
		size( 800, 800 );
	    textSize(15);
	    float angle = TWO_PI / numPulses;
	     
	    for (int i = 0; i < numPulses; i++) {
	        float x = cos( angle * i ) * radius;
	        float y = sin( angle * i ) * radius;
	        points[i] = new PVector( x, y );
	   }
	    for (int i = 0; i < numPulses; i++) {
	        float x = cos( angle * i ) * (radius + 20);
	        float y = sin( angle * i ) * (radius + 20);
	        charPoints[i] = new PVector( x, y );
	   } 
	    createLineCoordinates(new int[]{1,5,10,20});
	    
	  
	    
	    //noLoop();     
	}
	 
	public void draw(){	
		background(128);
	    smooth();
	    fill(0);
		renderCircle(width/4, height /4);
		renderCircle((width/4) * 3, height /4);
	}
	
	
	public void renderCircle(int xTranslate, int yTranslate){
		pushStyle();
		pushMatrix();
		fill(255);
		translate(xTranslate, yTranslate);
	    ellipse(0,0, radius * 2, radius * 2); //draws circle
	    
	  
	    
	    
	    //rect(-150, 200, radius*3, 300);
	    popStyle();
	    
	    //TO DO - refactor so less nested and more readable
	    for (int i = 0; i < numPulses; i++) {
	        for (int j = 0; j < numPulses; j++) {  	
	        	pushStyle();
	        	//noFill();
	        	ellipse(points[i].x, points[i].y, 5, 5); //draws an circle at each point
	        	popStyle();
	        	text(i, charPoints[i].x -5, charPoints[i].y +5); 
	        	if ( j != i ) {
	        		for(int[] onset: createLineCoordinates(onsets)){
	                	if(onset[0] == i && onset[1] == j){ // if we are at point in circle matching an onset
	                		line( points[i].x, points[i].y, points[j].x, points[j].y );
	                	}
	                }	
	             }
	         }
	      }
	    popMatrix();
	    //noLoop();	
	}

	/**
	 * Takes an array of numbers representing beat onsets in a sequence.
	 *
     * Based on single array of points to connect [1,3,6] which is returned by model
     * This class will need a method that takes this and assign the from / to nodes for each line
     * E.g. 1 -> 3, 3 -> 6, 6 -> 1  NOTE. the number 6 must point to the first element in the array
     * as its circular
     * 
     * 
     */
	private int[][] createLineCoordinates(int[] nodes){
		int[][] lineCoords = new int[nodes.length][2];
		for(int i = 0; i < nodes.length; i++){
			lineCoords[i][0] = nodes[i];
			if(i == nodes.length -1){
				lineCoords[i][1] = nodes[0];	
			} else {
				lineCoords[i][1] = nodes[i + 1];
			}
		}
		return lineCoords;
	}
	
	public void testMethod(){
		//println("hi");
		fill(0);
		
	}
	
	public void setOnsets(int[] onsets){
		this.onsets = onsets;
	}
	
	public void handleButtonEvents(GButton button, GEvent event){
		if (event == GEvent.CLICKED) {
			txaSample.setSelectedTextStyle(G4P.POSTURE, G4P.POSTURE_OBLIQUE);;
		}
	}

}
	
	


