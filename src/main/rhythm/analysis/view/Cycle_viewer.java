package rhythm.analysis.view;

import java.util.ArrayList;
import java.util.List;

import org.gicentre.utils.multisketch.EmbeddedSketch;





import processing.core.PVector;
import rhythm.analysis.control.Rhythm_controller;


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
	private PVector[] points;
	private PVector[] charPoints;
	private float radius;
	
	Arc_viewer arcViewerParent;
	private Rhythm_controller controller;
	
	
	public Cycle_viewer(Arc_viewer arcViewerParent,Rhythm_controller controller ){
		this.arcViewerParent = arcViewerParent;
		this.controller = controller;
		points = new PVector[this.controller.getNumPulses()];
		charPoints = new PVector[this.controller.getNumPulses()];
		radius = 70;
	}
	

	 
	public void setup() {
		
		
		size( 500, 300 );
	    textSize(15);
	    float angle = TWO_PI / this.controller.getNumPulses();
	     
	    for (int i = 0; i < this.controller.getNumPulses(); i++) {
	        float x = cos( angle * i ) * radius;
	        float y = sin( angle * i ) * radius;
	        points[i] = new PVector( x, y );
	   }
	    for (int i = 0; i < this.controller.getNumPulses(); i++) {
	        float x = cos( angle * i ) * (radius + 20);
	        float y = sin( angle * i ) * (radius + 20);
	        charPoints[i] = new PVector( x, y );
	   } 
	    //createLineCoordinates(new int[]{1,5,10,20});
	    
	  
	    
	    //noLoop();     
	}
	 
	public void draw(){	
		int[] onsets = new int[2];
		background(128);
	    smooth();
	    fill(0);
	    Integer[] testData = new Integer[]{1,5,10,11};

	    
	    
//	    for(int next :getOnsets(9)){
//	     println(next);	
//	    }
//	    println("----------------------");
//println("1: " + getOnsets(this.arcViewerParent.getSlider1()));
//println("2: " + getOnsets(this.arcViewerParent.getSlider2()));
//	    renderCircle(width/4, height /2, getOnsets(0));
//		renderCircle((width/4) * 3, height /2,  getOnsets(9));
	}
	
	
	public void renderCircle(int xTranslate, int yTranslate, Integer[] onsets){
		pushStyle();
		pushMatrix();
		fill(255);
		translate(xTranslate, yTranslate);
	    ellipse(0,0, radius * 2, radius * 2); //draws circle
	    
	    //rect(-150, 200, radius*3, 300);
	    popStyle();
	    
	    //TO DO - refactor so less nested and more readable
	    for (int i = 0; i < this.controller.getNumPulses(); i++) {
	        for (int j = 0; j < this.controller.getNumPulses(); j++) {  	
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
	private int[][] createLineCoordinates(Integer[] nodes){
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
	
	
	
	

	

}
	
	


