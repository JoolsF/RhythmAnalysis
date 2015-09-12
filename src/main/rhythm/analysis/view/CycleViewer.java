package rhythm.analysis.view;

import java.util.ArrayList;
import java.util.List;

import org.gicentre.utils.multisketch.EmbeddedSketch;





import processing.core.PVector;
import rhythm.analysis.control.RhythmController;


/**
 * CycleViewer class
 * Responsible for rendering the cycle viewer visualisation which shows two cycle (periods) as a polygon
 * http://www.openprocessing.org/sketch/27952 for connecting points in a circle
 * @author Julian Fenner
 * 
 */

public class CycleViewer extends EmbeddedSketch implements Observer  {
	private static final long serialVersionUID = 1L;
	private PVector[] points;
	private PVector[] charPoints;
	private float radius;
	
	private ArcViewer arcViewerParent;
	private RhythmController controller;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
	public CycleViewer(ArcViewer arcViewerParent,RhythmController controller ){
		this.arcViewerParent = arcViewerParent;
		this.controller = controller;
		points = new PVector[this.controller.getNumPulses()];
		charPoints = new PVector[this.controller.getNumPulses()];
		radius = 180;
		this.controller.attach(this);
	}

	
	/*-----------------------------------------------------------------------------------------
	 * Processing setup and draw methods
	 *----------------------------------------------------------------------------------------*/	
	 /**
	  * Processing setup method run immediately after constructor.
	  * Initialises key screen elements such as size and background.
	  * Run once
	  */
	public void setup() {
		size( 900, 500 );
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

	
	/**
     * Processing draw method runs in a loop immediately after setup() by default
     * Loop deactivated with noLoop() at end of method to avoid continual redrawing
	 */
	public void draw(){	
		setPoints();
		background(128);
	    smooth();
	    fill(0);

	    if(this.controller.getModelString().length() >= this.controller.getNumPulses()){
	    	
	    	int leftSlider = this.arcViewerParent.getleftSlider();
	    	int rightSlider = this.arcViewerParent.getRightSlider();
	    	
	    	renderCircle(width/4, height /2, getOnsets(leftSlider)); 
	    	renderCircle((width/4) * 3, height /2,  getOnsets(rightSlider));
	    }
	   noLoop();
	}
	
	/*
	 *Sets the circle points 
	 */
	private void setPoints(){
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
	

	private void renderCircle(int xTranslate, int yTranslate, Integer[] onsets){
		pushStyle();
		pushMatrix();
		fill(255);
		translate(xTranslate, yTranslate);
	    ellipse(0,0, radius * 2, radius * 2);
	    popStyle();
	    
	    //TO DO - refactor so less nested and more readable
	    for (int i = 0; i < this.controller.getNumPulses(); i++) {
	        for (int j = 0; j < this.controller.getNumPulses(); j++) {  	
	        	pushStyle();
	        	//noFill();
	        	ellipse(points[i].x, points[i].y, 5, 5); /*draws a circle at each point*/
	        	popStyle();
	        	text(i, charPoints[i].x -5, charPoints[i].y +5); 
	        	if ( j != i ) {
	        		for(int[] onset: createLineCoordinates(onsets)){
	                	if(onset[0] == i && onset[1] == j){ /* if we are at point in circle matching an onset*/
	                		line( points[i].x, points[i].y, points[j].x, points[j].y );
	                	}
	                }	
	             }
	         }
	      }
	    popMatrix();
	}

	/*
	 * Takes an array of numbers representing beat onsets in a sequence.
	 *
     * Based on single array of points in a cycle to connect e.g take [1,3,6] as the argument. 
     * This class will need a method that takes this and assign the from -> to nodes for each line drawn.
     * E.g. 1 -> 3, 3 -> 6, 6 -> 1  
     * NOTE. the number 6 must point to the first element in the array as its circular
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
	
	/*
	 * Takes string index as argument and returns the index of the 'onsets' within the period
	 * For example if period is 4 and string is 10011101 and argument value is 5 then it will return
	 * the position of the onsets at 1101 which are 4 5 and 7
	 */
	private Integer[] getOnsets(int sliderIndex){ 
		//TO DO - add exception handling here to deal with case when index is at end of string and period isn't "complete"
		List<Integer> result = new ArrayList<Integer>();
		int periodStart = sliderIndex - (sliderIndex %  controller.getNumPulses());
		int periodEnd = periodStart + (controller.getNumPulses() -1);
		char[] charArray = this.controller.getModelString().toCharArray();
		
		/*
		 * Length of character array must be >= periodEnd, if not there will be an ArrayIndexOutOfBoundsError.
		 * If this test is failed period start and period end must be moved back to nearest
		 * cycle start point
		 */
		if(periodEnd >= charArray.length){
			periodEnd = periodStart -1;
			periodStart = periodStart - this.controller.getNumPulses();
		}
		for(int i = periodStart; i < periodStart + controller.getNumPulses(); i++){	
			/*modulo num pulses because pulse must always be 0 to numpulses */
			if(charArray[i] == '1') result.add(i % this.controller.getNumPulses()); 			
		}	
		
		return result.toArray(new Integer[result.size()]);
	}
	

	/**
	 * Implements Observer interface's update method
	 */
	@Override
	public void update() {
		this.redraw();
	}
	
}