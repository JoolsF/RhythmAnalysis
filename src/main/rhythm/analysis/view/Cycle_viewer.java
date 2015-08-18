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
public class Cycle_viewer extends EmbeddedSketch implements Observer  {
	private static final long serialVersionUID = 1L;
	private PVector[] points;
	private PVector[] charPoints;
	private float radius;
	
	private Arc_viewer arcViewerParent;
	private Rhythm_controller controller;
	
	
	public Cycle_viewer(Arc_viewer arcViewerParent,Rhythm_controller controller ){
		this.arcViewerParent = arcViewerParent;
		this.controller = controller;
		points = new PVector[this.controller.getNumPulses()];
		charPoints = new PVector[this.controller.getNumPulses()];
		radius = 70;
		this.controller.attach(this);
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
	   
	}

	
	public void setPoints(){
		points = new PVector[this.controller.getNumPulses()];
		charPoints = new PVector[this.controller.getNumPulses()];
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
		
	}
	
	
	
	//TO DO - Needs further debugging and integrating with data model.  Render circle commented out therefore
	public void draw(){	
		setPoints();
		background(128);
	    smooth();
	    fill(0);

	    
	    if(this.controller.getModelString().length() >= this.controller.getNumPulses()){
	    	
	    	int leftSlider = this.arcViewerParent.getleftSlider();
	    	int rightSlider = this.arcViewerParent.getRightSlider();
	    	System.out.println("left");
	    	renderCircle(width/4, height /2, getOnsets(leftSlider)); //left slider
	    	
	    	System.out.println("right");
	    	renderCircle((width/4) * 3, height /2,  getOnsets(rightSlider)); //right slider
	    	
	    	System.out.println("------");
	    	System.out.println();
	    	System.out.println();
	    }
	   // noLoop();
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
	
	/***
	 * Take string index as argument and returns the index of the 'onsets' within the period
	 * For example if period is 4 and string is "10011101" and arg is 5 then it will return
	 * the position of the onsets at 1101 which are 4 5 and 7
	 * 
	 * @param index
	 * @return
	 */
	private Integer[] getOnsets(int sliderIndex){ //slider index
		//TO DO - add exception handling here to deal with case when index is at end of string and period isn't "complete"
		List<Integer> result = new ArrayList<Integer>();
		int periodStart = sliderIndex - (sliderIndex %  controller.getNumPulses());
		int periodEnd = periodStart + (controller.getNumPulses() -1);
		char[] charArray = this.controller.getModelString().toCharArray();
		
		
		//length of character array must >= periodEnd, if not there will be an ArrayIndexOutOfBoundsError
		//If this test is failed period start and period end must be moved back to to nearest
		//multiple of pulses number
		// i.e if array length is 10, period start is 8 and period end is 15 
		// then period start should be
//		println("period start" + periodStart);
//		println("period end" + periodEnd);
		if(periodEnd >= charArray.length){
			periodEnd = periodStart -1;
			periodStart = periodStart - this.controller.getNumPulses();
			
			println("-------------------");
			println();
			println();
			//println("i " + i);
			println("slider index " + sliderIndex);
			println("array length " + charArray.length);
			
			println("pulses " + controller.getNumPulses());
			println("new start" + (periodStart));
			println("new end" + (periodEnd));
		}
		for(int i = periodStart; i < periodStart + controller.getNumPulses(); i++){
			
			
			if(charArray[i] == '1') result.add(i % this.controller.getNumPulses()); //modulo num pulses because pulse must always be 0 to numpulses
			
		}	
		return result.toArray(new Integer[result.size()]);
		}
	
	private int correction(int x){
		int y = x - (x % this.controller.getNumPulses());
		return y;
	}


	@Override
	public void update() {
		this.redraw();
	}
	
}