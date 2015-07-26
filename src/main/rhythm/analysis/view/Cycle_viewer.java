package rhythm.analysis.view;

import org.gicentre.utils.multisketch.EmbeddedSketch;


import processing.core.PVector;

/*
 * REFERENCES
 * http://www.openprocessing.org/sketch/27952 for connecting points in a circle
 * 
 *
 */

//TO DO - Need to rotate circle -90 so that 0 is at 0 degrees.
public class Cycle_viewer extends EmbeddedSketch  {
	private static final long serialVersionUID = 1L;
	
	//TO DO - move to constructor
	private int numPulses = 30;
	private PVector[] points = new PVector[numPulses];
	private PVector[] charPoints = new PVector[numPulses];
	private float radius = 200;
	
	//Test data
	private int[] onsets = new int[]{};
	
	public Cycle_viewer(){
		
		
		
	}
	

	 
	public void setup() {
	    size( 500, 500 );
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

 
	    //test data start
	   /*
	    * Based on single array of points to connect [1,3,6] which is returned by model
	    * This class will need a method that takes this and assign the from / to nodes for each line
	    * E.g. 1 -> 3, 3 -> 6, 6 -> 1  NOTE. the number 6 must point to the first element in the array
	    * as its circular
	    * 
	    * 
	    */
	    
	    createLineCoordinates(new int[]{1,5,10,20});
	    noLoop();     
	}
	 
	public void draw(){	
		smooth();
	    background(100);
	    fill(0);
		renderCircle();
	}
	
	
	public void renderCircle(){
		translate(width/2, height/2);
	    pushStyle();
	    fill(255);
	    ellipse(0,0, radius * 2, radius * 2); //draws circle
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
	    noLoop();	
	}

	/**
	 * Takes an array of numbers representing beat onsets in a sequence.
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
		text("hello",20,20);
	}
	
	public void setOnsets(int[] onsets){
		this.onsets = onsets;
		//draw();
	}


}
	
	


