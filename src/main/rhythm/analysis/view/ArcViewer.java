package rhythm.analysis.view;
import java.util.List;

import processing.core.*;
import rhythm.analysis.control.RhythmController;


import org.gicentre.utils.multisketch.*;


import controlP5.ControlP5;
import controlP5.Textarea;
import controlP5.Textfield;
import g4p_controls.*;

/**
 * ArcViewer class
 * 
 *  This view class is a child view of the MainViewer class.  It is responsible for rendering the arcs
 *  and arc sliders.  
 * 
 *  Referenced
 *  https://processing.org/tutorials/text/
 *  https://processing.org/tutorials/transform2d/
 *  https://processing.org/tutorials/curves/
 *  http://www.gicentre.net/software/#/utils/
 *  Processing's 'Mouse Functions' demo referenced for custom slider
 *  
 *  @author Julian Fenner
*/

public class ArcViewer extends EmbeddedSketch implements Observer  {
	private static final long serialVersionUID = 1L;	

	/*-----------------------------------------------------------------------------------------
	 * Fields
	 *----------------------------------------------------------------------------------------*/
	private RhythmController controller;
		
	/*Screen size variables - Immutable*/
	public final int screenWidth, screenHeight, screenBorder, screenMidY, screenMidX;
	public final float lineLength;
	
	/*Pixel value of arc view x-axis subdivisions*/ 
	private float lineSubDivision;
	
	/*Processing fields*/
	private ControlP5 cp5;
	private PFont f;	
	private Textarea myTextarea;
	
	/*Arc sliders*/
	private ArcSlider leftSlider;
	private ArcSlider rightSlider;
	
	//Boolean value controlling whether arc colour applied
	private boolean applyColour;
	
	/*Node data from model. Stored locally to avoid continual polling.*/
	private List<List<Integer>> nodePairsExact;
	private List<List<Integer>> nodePairsSimilar;
	
	/*Child view windows*/
	private PopupWindow textViewWindow, cycleViewWindow;
	private TextViewer textViewer;
	private CycleViewer cycleViewer;
	private boolean windowsOpen;
	
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
			
	public ArcViewer(RhythmController controller){		
		/*Initialise controller*/
		this.controller = controller;
		/*Initialise windows*/
		this.controller.attach(this);
		
		/*Initialise screen dimensions*/
		this.screenWidth = 1000;
		this.screenBorder = 125;
		this.screenHeight = 800;
		//TO DO - 20 offset being used to correct centering issue.  Find cause and remove.
		this.screenMidY = (screenHeight / 2) - 20; 
		this.screenMidX = screenWidth / 2;
		this.lineLength = screenWidth - (screenBorder * 2);
		setLineSubDivision();
				
		/*Initialise sliders */
		this.leftSlider  = new ArcSlider(this, 15, screenBorder, screenMidX, screenBorder);
		this.rightSlider = new ArcSlider(this, 15, screenMidX, screenWidth - screenBorder, screenWidth - screenBorder);
		
		/* Get data from model */
		this.nodePairsExact = controller.getMatchingStrings();
		this.nodePairsSimilar = controller.getSimilarStrings();
	
		this.applyColour = true;
		
		/*Intialise child views */
		this.textViewer = new TextViewer(this, controller);
		controller.attach(textViewer);
		this.textViewWindow = new PopupWindow(this, textViewer); 
		
		this.cycleViewer = new CycleViewer(this, controller);
		controller.attach(cycleViewer);
		this.cycleViewWindow = new PopupWindow(this, cycleViewer);	
		
		this.windowsOpen = false;
		
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
		size(screenWidth, screenHeight);
		cp5 = new ControlP5(this);
		
		f = createFont("Georgia",15,true);
	    textFont(f);
	    fill(0);
	    textAlign(CENTER);
	    
	    /*Text area */
	    pushStyle();
	    myTextarea = cp5.addTextarea("txt")
	    	    .setPosition(20,25)
	    	    .setSize(200,200)
	    	    .setFont(createFont("arial",20))
	    	    .setLineHeight(20)
	    	    .setColor(color(00));	
	    popStyle();
	      
	 }
	 
	
	 /**
	   * Processing draw method runs in a loop immediately after setup()
	   */
	 public void draw()  {
		super.draw();   /* Should be the first line of draw().*/
		background(200, 255, 200); /*Should be second line of draw().*/ 
		setLineSubDivision();
		/*The five draw methods below should be called in this order to ensure layers overlap correctly */
		drawArcDiagram(nodePairsExact, -PI, 0);
		drawArcDiagram(nodePairsSimilar, 0, PI);
		drawArcXaxis();
		drawSliders();
	    drawTopLeftText();  
	 }
	 
	  
		/*-----------------------------------------------------------------------------------------
		 * Getters and setters
		 *----------------------------------------------------------------------------------------*/
		/**
		 * Gets left slider's position
		 * @return the left slider's position
		 */
		public int getleftSlider(){
			 return this.leftSlider.getSlider();
		 }
		
		/**
		 * Gets right slider's position
		 * @return the right slider's position
		 */
		public int getRightSlider(){
			return this.rightSlider.getSlider();
		}
		
		/**
		 * Gets node pair data
		 * @return the node pair data
		 */
		public List<List<Integer>> getNodePairs(){
			return this.nodePairsExact;
		}
		
		/**
		 * Updates the text viewer
		 */
		public void updateTextViewer(){
			this.textViewer.update();
		}
		
		/**
		 * Sets whether arc colour should be applied
		 * @param boolean value set to true is arc colour should be applied else false
		 */
		public void setArcColour(boolean flag){
			this.applyColour = flag;
		}
		/**
		 * Gets the line subdivision value of the arc viewer x-axis
		 * @return the line subdivision value of the arc viewer x-axis
		 */
		public float getLineSubdivision(){
			return this.lineSubDivision;
		}
		
		
		/*
		 *  Given that the first character of the string will be rendered at the start of line, the line should be 
		 *  divided into (line width / s.length()-1 ) sections. 
		 */
		private void setLineSubDivision(){
			// TO DO - handle case better where model data is empty string
			if(getData().length() == 0){
				this.lineSubDivision  = lineLength / 2;	
			} else {
				this.lineSubDivision  = lineLength / (getData().length() -1);
			}
		}
		
		private String getData(){
			return this.controller.getModelString();
		}	
		
			 
	 
	 /*-----------------------------------------------------------------------------------------
	  * Arc diagram draw methods
	  *----------------------------------------------------------------------------------------*/	
	 /*
	  * Draws arc diagram on the screen
	  * Reference - https://processing.org/reference/arc_.html
	  * Arcs connect repetition regions in a string.  
 	  * The width of the arc is the width of one of the repetition regions (they are the same)
 	  * Each arc has four args (int regionAstart, int regionAend, int regionBstart, int regionBend)   
	  * These represent the two repetition regions A and B to be joined.
	  * 
	  * In processing an arc is defined as follows 
      * arc(x, y, width, height, start, stop);
	  *	x represents the middle of the 'ellipse' which will be nodeTo - ((nodeTo position - nodeFrom position) / 2)
	  * As we need to take into account the width of the arc using strokeWeight(), we will need to calculate the
	  *	nodeFrom and nodeTo position as being midway between regionAstart  to regionAend and regionBstart to regionBstend 
	  * respectively
	  * The width will simply be nodeTo position - nodeFrom position
      *	The strokeWeight will be the distance regionAstart to regionAend 
      * Height can be any value for now  
	  */
	 private void drawArcDiagram(List<List<Integer>> nodePairs, float start, float stop){ 
		 for(List<Integer> next: nodePairs){	 
			int nodeDistance = next.get(1)- next.get(0);
			 		 
		 	if(nodeDistance >= this.controller.getArcMin() - 1){
		 	 float regionAstart = next.get(0) * lineSubDivision + screenBorder;
			 float regionAend = next.get(1) * lineSubDivision + screenBorder;
			 float regionBstart = next.get(2) * lineSubDivision + screenBorder;
			 float regionBend= next.get(3) * lineSubDivision + screenBorder;
			 float nodeLength = regionAend - regionAstart;
			 float nodeFrom = getMidPoint(regionAstart, regionAend);
			 float nodeTo = getMidPoint(regionBstart, regionBend);		 
			 float arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
			 float arcWidth = nodeTo - nodeFrom; 	
			 float arcHeight = arcWidth;
			 
			 /*Checks if the phase of the two arc are the same.*/
			 int arc1Phase = next.get(0) % this.controller.getNumPulses() -1;
			 int arc2Phase = next.get(2) % this.controller.getNumPulses() -1;
			 boolean phaseEqual = (arc1Phase == arc2Phase);
	
			 /*Deals with single character matches.*/
			 //TO DO  - Improve logic and build into algorithm above.
			 if(nodeDistance == 0)  nodeLength = 10;
	 
			 pushStyle(); 
			 noFill();
			 if(phaseEqual && this.applyColour){
				 if(arc1Phase == 0) {
					 stroke(204, 102, 2, 90);
				 } else {
					 stroke(0, 168, 204, 90);
				 }
			 } else{
			     stroke(100,90); /* 2nd arg is alpha value */	 
			 }
			 
			 strokeWeight(nodeLength);
			 strokeCap(SQUARE); /* Makes ends of arc square */	 
			 arc(arcMiddle, screenMidY, arcWidth,arcHeight, start, stop);
			 popStyle(); 		 
			}
		 }	 
		 
	 }
	 /*Helper method for drawArcDiagram */
	 private float getMidPoint(float x, float y){
		 return (x + y) / 2;
	 }
		 
	
	 /*-----------------------------------------------------------------------------------------
	  * Line axis draw methods
	  *----------------------------------------------------------------------------------------*/	
	 private void drawArcXaxis(){	 
		 /*
		  * Draw line.  
	 	  *	Y position = middle of screen i.e. (height / 2)
	 	  *	X position.  Line start  at (width + 100) and ends at (width - 100).  100 represents the border value.
		  */
		 line(screenBorder,screenMidY, screenWidth - screenBorder,screenMidY);
	
		/*
		 *  Render characters along line
		 *  If string length < 50 then whole string is shown along line
		 *  If string length > 50, line is divided into 50 subdivisions with each subdivision's value being = to l /50
		 */
		 float linePosition = screenBorder;
		 if(getData().length() <= 50){
			 for(char currentChar: getData().toCharArray()){
			    	pushMatrix();
			    	translate(linePosition, screenMidY);
			    	linePosition += lineSubDivision;
			    	text(currentChar,0, +30); //= 30 so that characters appear below the line
			    	ellipse(0,-0,5,5);
			    	popMatrix();
			  }
		 } else {
			 int x = getData().length() / 25;
			 for(int i = 1; i < getData().length(); i ++){
			 	pushMatrix();
		    	translate(linePosition, screenMidY);
		    	linePosition += lineSubDivision;
			    if(i % x == 0) {
			    	pushMatrix();
			    	rotate(-HALF_PI);
			    	//TO DO - make second variable b in text(a,b,c,d) below proportional to string length so that very large strings don't overlap x axis
			    	text(i + 1,-20, 0); /* i + 1 so that axis start from 1 not 0 */
			    	ellipse(0,-0,3,3);
			    	popMatrix();
			    }
		    	popMatrix(); 
			 }
		 }
	 }
	 
	 /*
	  * Helper method for draw() that draws sliders and checks if mouse is over any sliders
	  */
	 private void drawSliders(){
		 pushStyle();
		 noFill();
		 
		 ellipse(leftSlider.getXPixels(), screenMidY, leftSlider.getWidth(), leftSlider.getWidth());
		 leftSlider.setSlider();
		 leftSlider.checkSetOverSlider(mouseX, mouseY);
		 ellipse(rightSlider.getXPixels(), screenMidY, rightSlider.getWidth(), rightSlider.getWidth());
		 rightSlider.setSlider();
		 rightSlider.checkSetOverSlider(mouseX, mouseY);
		 updateCycleViewer();
		 popStyle();
		 
	 } 
	 
	 
	/*-----------------------------------------------------------------------------------------
	 * Other draw methods
	 *----------------------------------------------------------------------------------------*/
	
	 
	private void updateCycleViewer() {
		this.cycleViewer.redraw();
	}


	private void drawTopLeftText(){
		/* 1 added to left and right slider value so that it displays from 1 not 0 */
		 this.myTextarea.setText("Left slider: " +  (this.leftSlider.getSlider() + 1) +
					"\n" +
                 "Right slider: " + (this.rightSlider.getSlider() + 1) + 
                 "\nCycle length " + this.controller.getNumPulses());
	 }

	 
	 /*-----------------------------------------------------------------------------------------
	  * Mouse event handlers
	  *----------------------------------------------------------------------------------------*/
	 
	 public void mousePressed() {
		 leftSlider.setOffsetIfLocked(mouseX);
		 rightSlider.setOffsetIfLocked(mouseX);
		 
	 }
	
	 public void mouseDragged() {
		 //arguments ensure that sliders do not overlap
		 leftSlider.setSliderPixels(mouseX, leftSlider.getLeftMin(), rightSlider.getXPixels());
		 rightSlider.setSliderPixels(mouseX, leftSlider.getXPixels(), rightSlider.getRightMax());
		 
	 }	 
		
	 public void mouseReleased() {
	  leftSlider.lockSlider();
	  rightSlider.lockSlider();
	  //TO DO - Add button for this
	  if(this.windowsOpen == false) {
		  this.windowsOpen = true;
		  textViewWindow.setVisible(true);
		  this.textViewer.update();
	  //TO DO - Improve this logic.  If cycle viewer not needed it shouldn't be constructed.
	  if(this.controller.getNumPulses() > 1) cycleViewWindow.setVisible(true);  
		  
	  } 
	 
	}

	 /**
	  * Implements the update method from Observer interface.
	  */
	@Override
	public void update() {
		this.nodePairsExact = this.controller.getMatchingStrings();	
		this.nodePairsSimilar = this.controller.getSimilarStrings();	
	}	 
	 
}
